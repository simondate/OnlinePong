#!/bin/bash

cat <<+END+ > __convert3
s/\@Return/@return/g
+END+

#########################################
# Do not change beyond this line        #
#########################################

export DIRECTORY=`pwd`
cat <<+END+ > __convert2
echo \$1
sed -f $DIRECTORY/__convert3 < \$1 > \$1.xxx
mv \$1.xxx \$1
+END+

find . "(" -name "*.java" -o  -name "*.java" ")" \
       -exec sh $DIRECTORY/__convert2 {} ";"
rm -f __convert2 __convert3
