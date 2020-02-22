#!/usr/bin/env bash
if [ $# -ne 3 ]; then
    echo $0: usage: ./run.sh append ./tmp/Wrong-001.pdf ./tmp/Wrong-002.pdf
    exit 1
fi

echo $1 $2 $3
 java -jar ./build/libs/ITextPDFTest-1.jar $1 $2 $3
