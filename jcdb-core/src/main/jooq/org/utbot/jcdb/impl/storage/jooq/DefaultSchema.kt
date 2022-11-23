/*
 * This file is generated by jOOQ.
 */
package org.utbot.jcdb.impl.storage.jooq


import org.jooq.Catalog
import org.jooq.Table
import org.jooq.impl.SchemaImpl
import org.utbot.jcdb.impl.storage.jooq.tables.Annotations
import org.utbot.jcdb.impl.storage.jooq.tables.Annotationvalues
import org.utbot.jcdb.impl.storage.jooq.tables.Builders
import org.utbot.jcdb.impl.storage.jooq.tables.Bytecodelocations
import org.utbot.jcdb.impl.storage.jooq.tables.Calls
import org.utbot.jcdb.impl.storage.jooq.tables.Classes
import org.utbot.jcdb.impl.storage.jooq.tables.Classhierarchies
import org.utbot.jcdb.impl.storage.jooq.tables.Classinnerclasses
import org.utbot.jcdb.impl.storage.jooq.tables.Fields
import org.utbot.jcdb.impl.storage.jooq.tables.Methodparameters
import org.utbot.jcdb.impl.storage.jooq.tables.Methods
import org.utbot.jcdb.impl.storage.jooq.tables.Outerclasses
import org.utbot.jcdb.impl.storage.jooq.tables.Symbols


/**
 * This class is generated by jOOQ.
 */
@Suppress("UNCHECKED_CAST")
open class DefaultSchema : SchemaImpl("", DefaultCatalog.DEFAULT_CATALOG) {
    companion object {

        /**
         * The reference instance of <code>DEFAULT_SCHEMA</code>
         */
        val DEFAULT_SCHEMA = DefaultSchema()
    }

    /**
     * The table <code>Annotations</code>.
     */
    val ANNOTATIONS get() = Annotations.ANNOTATIONS

    /**
     * The table <code>AnnotationValues</code>.
     */
    val ANNOTATIONVALUES get() = Annotationvalues.ANNOTATIONVALUES

    /**
     * The table <code>Builders</code>.
     */
    val BUILDERS get() = Builders.BUILDERS

    /**
     * The table <code>BytecodeLocations</code>.
     */
    val BYTECODELOCATIONS get() = Bytecodelocations.BYTECODELOCATIONS

    /**
     * The table <code>Calls</code>.
     */
    val CALLS get() = Calls.CALLS

    /**
     * The table <code>Classes</code>.
     */
    val CLASSES get() = Classes.CLASSES

    /**
     * The table <code>ClassHierarchies</code>.
     */
    val CLASSHIERARCHIES get() = Classhierarchies.CLASSHIERARCHIES

    /**
     * The table <code>ClassInnerClasses</code>.
     */
    val CLASSINNERCLASSES get() = Classinnerclasses.CLASSINNERCLASSES

    /**
     * The table <code>Fields</code>.
     */
    val FIELDS get() = Fields.FIELDS

    /**
     * The table <code>MethodParameters</code>.
     */
    val METHODPARAMETERS get() = Methodparameters.METHODPARAMETERS

    /**
     * The table <code>Methods</code>.
     */
    val METHODS get() = Methods.METHODS

    /**
     * The table <code>OuterClasses</code>.
     */
    val OUTERCLASSES get() = Outerclasses.OUTERCLASSES

    /**
     * The table <code>Symbols</code>.
     */
    val SYMBOLS get() = Symbols.SYMBOLS

    override fun getCatalog(): Catalog = DefaultCatalog.DEFAULT_CATALOG

    override fun getTables(): List<Table<*>> = listOf(
        Annotations.ANNOTATIONS,
        Annotationvalues.ANNOTATIONVALUES,
        Builders.BUILDERS,
        Bytecodelocations.BYTECODELOCATIONS,
        Calls.CALLS,
        Classes.CLASSES,
        Classhierarchies.CLASSHIERARCHIES,
        Classinnerclasses.CLASSINNERCLASSES,
        Fields.FIELDS,
        Methodparameters.METHODPARAMETERS,
        Methods.METHODS,
        Outerclasses.OUTERCLASSES,
        Symbols.SYMBOLS
    )
}
