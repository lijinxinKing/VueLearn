#!/bin/bash
workspace=$(cd $(dirname $0) && pwd -P)
cd $workspace
gitversion=RELEASE
control=./control.sh
action=$1
app=kcops
source /root/.nvm/nvm.sh

if [ "$#" != "1" ]; then
    echo -e "Usage: \n\t./$0 [prod|prev|test|dev]"
    exit 1
fi

case $action in
	"prod")
	    environment="prod"
            nvm use v8.11.4
	;;
	"prev")
	    environment="prev"
	;;
	"test")
	    environment="test"
	;;
	*)
	    environment="dev"
	;;
esac

function build() {
    echo "Enviroment: ${environment}"
    # 新建output目录
    local output="./output"
    rm -rf $output &>/dev/null
    mkdir -p $output &>/dev/null

    # 进行编译
    #nvm use v8.11.4
    npm install
    #npm run rd_build
    npm run build:prod
    echo -n "$app build ok, vsn="
    rm -rf $gitversion
    gitversion
}

function make_output() {
    local output="./output"
    # 填充output目录, output内的内容 即为 线上部署内容
    (
      \cp -r ./dist/* $output/;
      echo -e "make output ok."
    ) || { echo -e "make output failed!"; exit 2; }

}

## internals
function gitversion() {
    version=$(git status | sed -n '1p' | awk '{print $NF}')
    echo "Version: ${version}" >> $gitversion
    revision=$(git rev-parse HEAD)
    echo "Revision: ${revision}" >> $gitversion
    local gv=`cat $gitversion`
    echo "$gv"
}


##########################################
## main
## 其中,
##        1.下载依赖
##        2.编译
##        3.生成output目录
##        4.打包output
##########################################

# 1.进行编译
build

# 2.生成部署包output
make_output

# 编译成功
echo -e "build done."
exit 0