#!/bin/env bash
mkdir ../$1 || exit
this=$(basename "$0")
cp --parents -rvn $(git ls-files|grep -v $this) ../$1 &&
cd ../$1 &&
git init &&
user=$(git config --global credential.git@github.com.username)
git remote add origin git@github.com:$user/$1.git &&
sed -i -e "s/{{project_name}}/$1/g" *.gradle.kts README.md
sed -i -e "s/{{project_description}}/$2/g" *.gradle.kts README.md