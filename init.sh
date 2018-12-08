#!/bin/env bash
mkdir ../$1 || exit
cp --parents -rvn $(git ls-files|grep -v $0) ../$1 &&
cd ../$1 &&
git init &&
user=$(git config --global credential.git@github.com.username)
git remote add origin git@github.com:$user/$1.git &&
sed -i -e "s/{{project_name}}/$1/g" settings.gradle