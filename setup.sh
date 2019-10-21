#!/bin/bash
dir=$(pwd|rev|cut -d "/" -f 1|rev)
desc=$1
files=$(/bin/ls -1|egrep -i '(.kts|.md)$')
sed -i -e "s/{{project_name}}/$dir/g" $files
sed -i -e "s/{{project_description}}/$desc/g" $files
