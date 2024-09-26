#/bin/bash
set -euo pipefail

if [[ -z "${ARKANALYZER_DIR}" ]]; then
  echo "ARKANALYZER_DIR is undefined"
  exit 1
fi

echo "ARKANALYZER_DIR=${ARKANALYZER_DIR}"
SCRIPT=$ARKANALYZER_DIR/src/save/serializeArkIR.ts

pushd "$(dirname $0)" >/dev/null
mkdir -p projects
pushd projects

function check_repo() {
  if [[ $# -ne 1 ]]; then
    echo "Usage: check_repo <repo>"
    exit 1
  fi
  if [[ ! -d $1 ]]; then
    echo "Repository not found: $1"
    exit 1
  fi
}

function prepare_project_dir() {
  if [[ $# -ne 1 ]]; then
    echo "Usage: prepare_project <name>"
    exit 1
  fi
  NAME=$1
  echo
  echo "=== Preparing $NAME..."
  echo
  if [[ -d $NAME ]]; then
    echo "Directory already exists: $NAME"
    exit
  fi
  mkdir -p $NAME
  pushd $NAME
}

function prepare_module() {
  if [[ $# -ne 2 ]]; then
    echo "Usage: prepare_module <module> <root>"
    exit 1
  fi
  local MODULE=$1
  local ROOT=$2
  echo "= Preparing module: $MODULE"
  local SRC="source/$MODULE"
  local ETSIR="etsir/$MODULE"
  mkdir -p $(dirname $SRC)
  echo "Linking sources..."
  echo "pwd = $(pwd)"
  ln -srfT "$ROOT/src/main/ets" $SRC
  echo "Serializing..."
  npx ts-node $SCRIPT -p $SRC $ETSIR -v
}

(
  prepare_project_dir "ArkTSDistributedCalc"

  REPO="../../repos/applications_app_samples"
  check_repo $REPO

  prepare_module "entry" "$REPO/code/SuperFeature/DistributedAppDev/ArkTSDistributedCalc/entry"
)

(
  prepare_project_dir "AudioPicker"

  REPO="../../repos/applications_filepicker"
  check_repo $REPO

  prepare_module "entry" "$REPO/entry"
  prepare_module "audiopicker" "$REPO/audiopicker"
)

(
  prepare_project_dir "Launcher"

  REPO="../../repos/applications_launcher"
  check_repo $REPO

  prepare_module "launcher_common" "$REPO/common"
  prepare_module "launcher_appcenter" "$REPO/feature/appcenter"
  prepare_module "launcher_bigfolder" "$REPO/feature/bigfolder"
  prepare_module "launcher_form" "$REPO/feature/form"
  prepare_module "launcher_gesturenavigation" "$REPO/feature/gesturenavigation"
  prepare_module "launcher_numbadge" "$REPO/feature/numbadge"
  prepare_module "launcher_pagedesktop" "$REPO/feature/pagedesktop"
  prepare_module "launcher_recents" "$REPO/feature/recents"
  prepare_module "launcher_smartDock" "$REPO/feature/smartdock"
  prepare_module "phone_launcher" "$REPO/product/phone"
  prepare_module "pad_launcher" "$REPO/product/pad"
  prepare_module "launcher_settings" "$REPO/feature/settings"
)

(
  prepare_project_dir "Settings"

  REPO="../../repos/applications_settings"
  check_repo $REPO

  prepare_module "phone" "$REPO/product/phone"
  # prepare_module "wearable" "$REPO/product/wearable"
  prepare_module "component" "$REPO/common/component"
  prepare_module "search" "$REPO/common/search"
  ### prepare_module "settingsBase" "$REPO/common/settingsBase"
  prepare_module "utils" "$REPO/common/utils"
)
