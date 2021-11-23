<template>
    <div>
        <el-container>
            <el-header class="homeHeader">
                <div class="title">云E办</div>
                <div>
                    <!-- <el-button icon="el-icon-bell"
                               type="text"
                               size="normal"
                               style="margin-right: 8px;color: black"
                               @click="goChat"></el-button> -->
                    <el-dropdown class="userInfo" @command="commandHandler">
                      <span class="el-dropdown-link">
                        {{user.name}}<i><img :src="user.userFace"></i>
                      </span>
                        <el-dropdown-menu slot="dropdown">
                            <el-dropdown-item command="userinfo">个人中心</el-dropdown-item>
                            <el-dropdown-item command="setting">设置</el-dropdown-item>
                            <el-dropdown-item command="logout">注销登录</el-dropdown-item>
                        </el-dropdown-menu>
                    </el-dropdown>
                </div>
            </el-header>
        </el-container>
    </div>
</template>
<script>
export default {
    name: "Home"
}
</script>
<style scoped>
</style>