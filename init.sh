#!/bin/env bash
mkdir ../$1 || exit
cp --parents -rvn $(git ls-files) ../$1 &&
cd ../$1 &&
git init &&
git remote add origin git@github.com:mantono/$1.git &&
sed -i -e "s/{{project_name}}/$1/g" settings.gradle