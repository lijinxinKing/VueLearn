<template>
    <div>
        <el-tabs v-model="activeName" type="card">
            <el-tab-pane label="项目管理" name="DepMana"><DepMana></DepMana></el-tab-pane>
            <!-- <el-tab-pane label="职位管理" name="PosMana"><PosMana></PosMana></el-tab-pane>
            <el-tab-pane label="职称管理" name="JoblevelMana"><JoblevelMana></JoblevelMana></el-tab-pane> -->
            <!-- <el-tab-pane label="权限组" name="PermissMana"><PermissMana></PermissMana></el-tab-pane> -->
        </el-tabs>
    </div>
</template>

<script>
    import DepMana from '../../components/sys/basic/DepMana'

    export default {
        name: "SysBasic",
        data() {
            return {
                activeName: 'DepMana'
            }
        },
        components:{
            DepMana
        }
    }
</script>

<style scoped>

</style>