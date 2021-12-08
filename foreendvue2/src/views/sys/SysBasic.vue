<template>
<div>
    <el-tabs v-model="activeName" type="card">
        <el-tab-pane label="部门管理" name="depmana"></el-tab-pane>
        <el-tab-pane label="职位管理" name="posmana"></el-tab-pane>
        <el-tab-pane label="职称管理" name="JoblevelMana"></el-tab-pane>
        <el-tab-pane label="奖惩规则" name="ecmana"></el-tab-pane>
        <el-tab-pane label="权限组" name="permissmana"></el-tab-pane>
</el-tabs>
</div>
</template>
<script>
export default {
    name: "SysBasic",
    data(){
        return{
            activeName:'PosMana'
        }
    }
}
</script>
<style>
</style>