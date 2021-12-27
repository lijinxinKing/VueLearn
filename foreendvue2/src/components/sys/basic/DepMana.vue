<template>
    <div style="width: 500px">
        <el-input
                placeholder="请输入项目名称进行搜索..."
                prefix-icon="el-icon-search"
                v-model="filterText">
        </el-input>
        <el-tree
                :data="deps"
                :props="defaultProps"
                default-expand-all
                :filter-node-method="filterNode"
                :expand-on-click-node="false"
                ref="tree">
            <span class="custom-tree-node" slot-scope="{ node, data }"
                  style="display: flex;justify-content: space-between;width: 100%">
                <span>{{ data.name }}</span>
                <span>
                  <el-button
                          type="primary"
                          size="mini"
                          class="depBtn"
                          @click="() => showAddDep(data)">
                    添加项目
                  </el-button>
                  <el-button
                          type="danger"
                          size="mini"
                          class="depBtn"
                          @click="() => deleteDep(data)">
                    删除项目
                  </el-button>
                </span>
            </span>
        </el-tree>
        <el-dialog
                title="添加项目"
                :visible.sync="dialogVisible"
                width="30%">
            <div>
                <table>
                    <tr>
                        <td>
                            <el-tag>上级项目</el-tag>
                        </td>
                        <td>{{pname}}</td>
                    </tr>
                    <tr>
                        <td>
                            <el-tag>项目名称</el-tag>
                        </td>
                        <td>
                            <el-input v-model="dep.name" placeholder="请输入项目名称..."></el-input>
                        </td>
                    </tr>
                </table>
            </div>
            <span slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="doAddDep">确 定</el-button>
          </span>
        </el-dialog>
    </div>
</template>

<script>
    export default {
        name: "DepMana",
        data() {
            return {
                filterText: '',
                deps: [{
                    id:'',
                    name:''
                }],
                defaultProps: {
                    children: 'components',
                    label: 'name'
                },
                dialogVisible: false,
                dep: {
                    name: '',
                    parentId: -1
                },
                pname: ''
            }
        },
        watch: {
            filterText(val) {
                this.$refs.tree.filter(val);
            }
        },
        mounted() {
            this.initDeps();
        },
        methods: {
            initDep() {
                this.dep = {
                    name: '',
                    parentId: -1
                }
                this.pname = '';
            },
            addDep2Deps(deps, dep) {
                for (let i = 0; i < deps.length; i++) {
                    let d = deps[i];
                    if (d.id == dep.parentId) {
                        d.components = d.components.concat(dep);
                        if (d.components.length > 0) {
                            d.isParent = true;
                        }
                        return;
                    } else {
                        this.addDep2Deps(d.components, dep);
                    }
                }
            },
            doAddDep() {
                this.postRequest('/component/basic/addComponent', this.dep).then(resp => {
                    if (resp) {
                        this.addDep2Deps(this.deps, resp.obj);
                        this.dialogVisible = false;
                        this.initDep();
                    }
                })
            },
            showAddDep(data) {
                this.dep.parentId = data.id;
                this.pname = data.name;
                this.dialogVisible = true;
            },
            removeDepFromDeps(p, deps, id) {
                for (let i = 0; i < deps.length; i++) {
                    let d = deps[i];
                    if (d.id == id) {
                        deps.splice(i, 1);
                        if (deps.length == 0) {
                            p.isParent = false;
                        }
                        return;
                    } else {
                        this.removeDepFromDeps(d, d.components, id);
                    }
                }
            },
            deleteDep(data) {
                if (data.isParent) {
                    this.$message.error('父项目删除失败！');
                } else {
                    this.$confirm('此操作将永久删除该[' + data.name + ']项目, 是否继续?', '提示', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }).then(() => {
                        this.deleteRequest('/component/basic/' + data.id).then(resp => {
                            if (resp) {
                                this.removeDepFromDeps(null, this.deps, data.id);
                            }
                        })
                    }).catch(() => {
                        this.$message({
                            type: 'info',
                            message: '已取消删除'
                        });
                    });
                }
            },
            initDeps() {
                this.getRequest('/component/basic/getAllComponent').then(resp => {
                    if (resp) {
                        this.deps = resp.obj
                    }
                })
            },
            filterNode(value, data) {
                if (!value) return true;
                return data.name.indexOf(value) !== -1;
            }
        }
    }
</script>

<style>
    .depBtn {
        padding: 2px;
    }

</style>