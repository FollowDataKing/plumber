#!/bin/sh

function usage()
{
    echo "if this was a real script you would see something useful here"
    echo ""
    echo "$0"
    echo "\t-h --help"
    echo "\t--master <spark_marter_url>"
    echo "\t--task <plumber_task>"
    echo ""
}

if [ -z $SPARK_HOME ]; then
    echo "Environment variable 'SPARK_HOME' is not set!"
    exit
fi

MASTER="local[2]"
TASK=""

while [ "$1" != "" ]; do
    PARAM=`echo $1 | awk -F= '{print $1}'`
    VALUE=`echo $1 | awk -F= '{print $2}'`

    case $PARAM in
        -h | --help)
            usage
            exit
            ;;
        --master)
            if [ -z $2 ]; then
                echo "ERROR: missing value for parameter '--master'"
                usage
                exit
            fi
            MASTER=$2
            shift
            ;;
        --task)
            if [ -z $2 ]; then
                echo "ERROR: missing value for parameter '--task'"
                usage
                exit
            fi
            TASK=$2
            shift
            ;;
        *)
            echo "ERROR: unknown parameter \"$PARAM\""
            usage
            exit 1
            ;;
    esac
    shift
done

PLUMBER_VERSION="0.1-SNAPSHOT"


echo "Welcome to"
echo "               __             __              "
echo "         ___  / /  __ ___  _ / /_  ___  ____  "
echo "        / _ \/ /__/ // / \/ \  _ \/ __\/ __/  "
echo "       / .__/____/____/_\__\_\___/\__\/_/     version ${PLUMBER_VERSION}"
echo "      /_/                                     "


echo "Found the spark home [$SPARK_HOME]"
echo "Using the spark master: $MASTER"
echo "Using the plumber task: $TASK"

echo "${SPARK_HOME}/bin/spark-submit --master ${MASTER} --class org.plumber.PlumberApp ./plumber-assembly-${PLUMBER_VERSION}.jar"
`${SPARK_HOME}/bin/spark-submit --master ${MASTER} --class org.plumber.PlumberApp ../libs/plumber-assembly-${PLUMBER_VERSION}.jar`
