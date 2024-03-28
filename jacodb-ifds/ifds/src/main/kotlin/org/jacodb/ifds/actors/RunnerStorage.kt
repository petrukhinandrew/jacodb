/*
 *  Copyright 2022 UnitTestBot contributors (utbot.org)
 * <p>
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 * <p>
 *  http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.jacodb.ifds.actors

import org.jacodb.actors.api.Actor
import org.jacodb.actors.api.ActorContext
import org.jacodb.actors.api.ActorRef
import org.jacodb.ifds.domain.Edge
import org.jacodb.ifds.domain.RunnerType
import org.jacodb.ifds.domain.Vertex
import org.jacodb.ifds.messages.CommonMessage
import org.jacodb.ifds.messages.EdgeMessage
import org.jacodb.ifds.messages.NewEdge
import org.jacodb.ifds.messages.NewResult
import org.jacodb.ifds.messages.NewSummaryEdge
import org.jacodb.ifds.messages.NotificationOnEnd
import org.jacodb.ifds.messages.NotificationOnStart
import org.jacodb.ifds.messages.ObtainResults
import org.jacodb.ifds.messages.StorageMessage
import org.jacodb.ifds.messages.SubscriptionOnEnd
import org.jacodb.ifds.messages.SubscriptionOnStart

context(ActorContext<StorageMessage>)
class RunnerStorage<Stmt, Fact>(
    private val parent: ActorRef<CommonMessage>,
    private val runnerType: RunnerType,
) : Actor<StorageMessage> {
    data class SubscriptionData<Stmt, Fact>(
        val edge: Edge<Stmt, Fact>,
        val subscriber: RunnerType,
    )

    private val endSubscribers =
        HashMap<Vertex<Stmt, Fact>, HashSet<SubscriptionData<Stmt, Fact>>>()
    private val startSubscribers =
        HashMap<Vertex<Stmt, Fact>, HashSet<SubscriptionData<Stmt, Fact>>>()

    private val edges = hashSetOf<Edge<Stmt, Fact>>()
    private val reasons = hashMapOf<Edge<Stmt, Fact>, HashSet<Edge<Stmt, Fact>>>()

    private val summaryEdges = hashSetOf<Edge<Stmt, Fact>>()
    private val summaryEdgesByStart = hashMapOf<Vertex<Stmt, Fact>, HashSet<Edge<Stmt, Fact>>>()
    private val summaryEdgesByEnd = hashMapOf<Vertex<Stmt, Fact>, HashSet<Edge<Stmt, Fact>>>()

    private val foundResults = hashSetOf<Any?>()

    override suspend fun receive(message: StorageMessage) {
        when (message) {
            is NewEdge<*, *> -> {
                @Suppress("UNCHECKED_CAST")
                message as NewEdge<Stmt, Fact>
                val edge = message.edge

                reasons
                    .computeIfAbsent(edge) { hashSetOf() }
                    .add(message.reason)

                if (edges.add(edge)) {
                    // new edge
                    parent.send(EdgeMessage(edge))

                }
            }

            is NewSummaryEdge<*, *> -> {
                @Suppress("UNCHECKED_CAST")
                message as NewSummaryEdge<Stmt, Fact>

                val edge = message.edge
                if (summaryEdges.add(edge)) {
                    summaryEdgesByStart
                        .computeIfAbsent(edge.from) { hashSetOf() }
                        .add(edge)
                    summaryEdgesByEnd
                        .computeIfAbsent(edge.to) { hashSetOf() }
                        .add(edge)

                    // subscriptions
                    sendStartNotifications(edge)
                    sendEndNotifications(edge)
                }
            }

            is SubscriptionOnStart<*, *> -> {
                @Suppress("UNCHECKED_CAST")
                message as SubscriptionOnStart<Stmt, Fact>

                val subscriptionData = SubscriptionData(message.data, message.subscriber)

                sendStartNotificationsOnExistingSummaryEdges(message.startVertex, subscriptionData)

                startSubscribers
                    .computeIfAbsent(message.startVertex) { hashSetOf() }
                    .add(subscriptionData)
            }

            is SubscriptionOnEnd<*, *> -> {
                @Suppress("UNCHECKED_CAST")
                message as SubscriptionOnEnd<Stmt, Fact>

                val subscriptionData = SubscriptionData(message.data, message.subscriber)

                sendEndNotificationsOnExistingSummaryEdges(message.endVertex, subscriptionData)

                endSubscribers
                    .computeIfAbsent(message.endVertex) { hashSetOf() }
                    .add(subscriptionData)
            }

            is NewResult -> {
                foundResults.add(message.result)
            }

            is ObtainResults -> {
                message.channel.send(foundResults.toList())
            }
        }
    }

    private suspend fun sendStartNotificationsOnExistingSummaryEdges(
        vertex: Vertex<Stmt, Fact>,
        subscriptionData: SubscriptionData<Stmt, Fact>,
    ) {
        val summaries = summaryEdgesByStart.getOrDefault(vertex, emptySet())
        for (summaryEdge in summaries) {
            val notification = NotificationOnStart(
                subscriptionData.subscriber,
                runnerType,
                summaryEdge,
                subscriptionData.edge
            )
            parent.send(notification)
        }
    }

    private suspend fun sendEndNotificationsOnExistingSummaryEdges(
        vertex: Vertex<Stmt, Fact>,
        subscriptionData: SubscriptionData<Stmt, Fact>,
    ) {
        val summaries = summaryEdgesByEnd.getOrDefault(vertex, emptySet())
        for (summaryEdge in summaries) {
            val notification = NotificationOnEnd(
                subscriptionData.subscriber,
                runnerType,
                summaryEdge,
                subscriptionData.edge
            )
            parent.send(notification)
        }
    }


    private suspend fun sendStartNotifications(edge: Edge<Stmt, Fact>) {
        val currentEdgeStartSubscribers = startSubscribers
            .getOrDefault(edge.from, emptySet())

        for ((data, subscriber) in currentEdgeStartSubscribers) {
            val notification = NotificationOnStart(
                subscriber,
                runnerType,
                edge,
                data
            )
            parent.send(notification)
        }
    }

    private suspend fun sendEndNotifications(edge: Edge<Stmt, Fact>) {
        val currentEdgeEndSubscribers = endSubscribers
            .getOrDefault(edge.to, emptySet())

        for ((data, subscriber) in currentEdgeEndSubscribers) {
            val notification = NotificationOnEnd(
                subscriber,
                runnerType,
                edge,
                data
            )
            parent.send(notification)
        }
    }
}