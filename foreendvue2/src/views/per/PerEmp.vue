<template>
    <div>
        <div>
            <div style="display: flex;justify-content: space-between">
                <div>
                    <el-input style="width: 300px;margin-right: 10px"
                              prefix-icon="el-icon-search"
                              v-model="empName"
                              @keydown.enter.native="initEmps"
                              clearable
                              @clear="initEmps"
                              :disabled="showAdvanceSearchVisible"
                              placeholder="请输入机器IP进行搜索..."></el-input>
                    <el-button type="primary" icon="el-icon-search" @click="initEmps"
                               :disabled="showAdvanceSearchVisible">搜索
                    </el-button>
                    <el-button type="primary" @click="showAdvanceSearchVisible = !showAdvanceSearchVisible">
                        <i :class="showAdvanceSearchVisible?'fa fa-angle-double-up':'fa fa-angle-double-down'"
                           aria-hidden="true"></i>
                        主要操作
                    </el-button>
                    <el-row style="width: 300px;margin-top: 10px"/>
                <div>
                <template>
                 <el-tabs v-model="activeName" type="card" @tab-click="handleClick">
                   <el-tab-pane label="Vantage" name="Vantage"></el-tab-pane>
                   <el-tab-pane label="TPNDA" name="TPNDA"></el-tab-pane>
                   <el-tab-pane label="Utility" name="Utility"></el-tab-pane>
                   <el-tab-pane label="Lenovo Smartkey" name="Lenovo Smartkey"></el-tab-pane>
                 </el-tabs>
                </template>
                <el-row style="width: 300px;margin-top: 10px"/>
                <el-radio-group v-model="componentId" @change="getChanged()">
                  <el-radio v-for="opt in danoptions[this.radioIndex]" border @change="handleRadioChanges(index,opt.label)" :key="opt.label" :label="opt.label">
                    {{opt.value}}
                  </el-radio>
                </el-radio-group>
              </div>
                </div>
                <div>
                    <el-upload
                            style="display: inline-flex;margin-right: 8px"
                            :headers="headers"
                            :show-file-list="false"
                            :before-upload="beforeUpload"
                            :on-success="onSuccess"
                            :on-error="onError"
                            :disbaled="importDataDisabled"
                            action=""> <!-- /employee/basic/import -->
                        <el-button type="success" :icon="importDataBtnIcon" :disabled="importDataDisabled">
                            {{importDataBtnText}}
                        </el-button>
                    </el-upload>
                    <el-button @click="exportData" type="success" icon="el-icon-download">
                        列出Component
                    </el-button>
                    <el-button type="primary" icon="el-icon-plus" @click="showAddEmpView">添加机器</el-button>
                </div>
            </div>
        </div>
        <transition name="slide-fade">
            <div v-show="showAdvanceSearchVisible"
                 style="border: 1px solid #409eff;border-radius: 5px;box-sizing: border-box;padding: 5px;margin: 10px 0px">
                <el-row>
              <el-form :inline="true" :model="formInline" class="demo-form-inline" style="margin-top: 20px">
                <el-col>
                                  <el-form-item>
                  <el-button
                    type="primary"
                    style="margin-right:0.5cm;"
                    @click="onEventClick($event)"
                  >Clear Cache</el-button>
                  <el-button
                    type="primary"
                    style="margin-right:0.5cm;"
                    @click="onEventClick($event)"
                  >Clear Cache And Reboot</el-button>
                  <el-button
                    type="primary"
                    style="margin-right:0.5cm;"
                    @click="onEventClick($event)"
                  >Shut Down
                  </el-button>
                  <el-button
                    type="primary"
                    style="margin-right:0.5cm;"
                    @click="onEventClick($event)"
                  >Update Client
                  </el-button>
                </el-form-item>
                </el-col>
                <el-col>
                  <el-form-item>
                  <el-button
                    type="primary"
                    style="margin-right:0.5cm;"
                    @click="onEventClick($event)"
                  >Disable System Update
                  </el-button>
                  <el-button
                    type="primary"
                    style="margin-right:0.5cm;"
                    @click="onEventClick($event)"
                  >Enable System Update
                  </el-button>
                  <el-button
                    type="primary"
                    style="margin-right:0.5cm;"
                    @click="onEventClick($event)"
                  >Get Machine Info
                  </el-button>
                  <el-button
                    type="primary"
                    style="margin-right:0.5cm;"
                    @click="onEventClick($event)"
                  >Rerun
                  </el-button>
                  <el-button
                    type="primary"
                    style="margin-right:0.5cm;"
                    @click="onEventClick($event)"
                  >Get Report
                  </el-button>
                </el-form-item>
                </el-col>
              </el-form>
                </el-row>
            </div>
        </transition>
        <div style="margin-top: 10px">
            <el-table
                    :data="emps"
                    stripe
                    border
                    v-loading="loading"
                    element-loading-text="拼命加载中"
                    element-loading-spinner="el-icon-loading"
                    element-loading-background="rgba(0, 0, 0, 0.8)"
                    style="width: 100%">
                <el-table-column
                        type="selection"
                        width="55">
                </el-table-column>
                <el-table-column
                        prop="id"
                        label="id"
                        fixed
                        align="left"
                        width="90">
                </el-table-column>
                <el-table-column
                        prop="fkComponent"
                        label="工号"
                        align="left"
                        width="85">
                </el-table-column>
                <el-table-column
                        prop="isOk"
                        label="连接状态"
                        width="50">
                </el-table-column>
                <el-table-column
                        prop="isIdle"
                        label="空闲状态"
                        align="left"
                        width="95">
                </el-table-column>
                <el-table-column
                        prop="user"
                        label="使用人"
                        align="left"
                        width="100">
                </el-table-column>
                <!-- <el-table-column
                        prop="currentVersion"
                        label="版本"
                        width="70">
                </el-table-column> -->
                <!-- <el-table-column
                        prop="currentServer"
                        label="服务器"
                        width="80">
                </el-table-column> -->
                <el-table-column
                        prop="machineName"
                        label="机器名称"
                        width="100">
                </el-table-column>
                <el-table-column
                        prop="ip"
                        label="IP地址"
                        align="left"
                        width="120">
                </el-table-column>
                <el-table-column
                        prop="connected"
                        label="连接状态"
                        align="left"
                        width="100">
                </el-table-column>
                <el-table-column
                        prop="running"
                        label="运行"
                        align="left"
                        width="50">
                </el-table-column>
                <el-table-column
                        prop="ctime"
                        label="开始日期"
                        align="left"
                        width="150">
                </el-table-column>
                <el-table-column
                        prop="utime"
                        label="结束日期"
                        width="150">
                </el-table-column>
                <el-table-column
                        prop="reportAddress1"
                        label="报告地址"
                        width="100">
                </el-table-column>
                <el-table-column
                        label="操作"
                        fixed="right"
                        width="200">
                    <template slot-scope="scope">
                        <el-button @click="showEditEmpView(scope.row)" style="padding: 3px" size="mini">编辑</el-button>
                        <el-button style="padding: 3px" size="mini" type="primary">查看高级资料</el-button>
                        <el-button @click="deleteEmp(scope.row)" style="padding: 3px" size="mini" type="danger">删除
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div style="display: flex;justify-content: flex-end">
                <el-pagination
                        background
                        @current-change="currentChange"
                        @size-change="sizeChange"
                        layout="sizes,prev, pager, next, jumper, ->, total"
                        :total="total">
                </el-pagination>
            </div>
        </div>
        <el-dialog
                :title="title"
                :visible.sync="dialogVisible"
                width="80%">
            <div>
                <el-form ref="empForm" :model="emp" :rules="rules">
                    <el-row>
                        <el-col :span="6">
                            <el-form-item label="姓名：" prop="name">
                                <el-input size="mini" prefix-icon="el-icon-edit" style="width: 150px;"
                                          v-model="emp.name" placeholder="请输入员工姓名"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="5">
                            <el-form-item label="性别：" prop="gender">
                                <el-radio-group v-model="emp.gender" style="margin-top: 8px">
                                    <el-radio label="男">男</el-radio>
                                    <el-radio label="女">女</el-radio>
                                </el-radio-group>
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="出生日期：" prop="birthday">
                                <el-date-picker
                                        v-model="emp.birthday"
                                        type="date"
                                        size="mini"
                                        style="width: 150px"
                                        value-format="yyyy-MM-dd"
                                        placeholder="出生日期">
                                </el-date-picker>
                            </el-form-item>
                        </el-col>
                        <el-col :span="7">
                            <el-form-item label="政治面貌：" prop="politicId">
                                <el-select v-model="emp.politicId" size="mini" style="width: 200px" placeholder="政治面貌">
                                    <el-option
                                            v-for="item in politicsstatus"
                                            :key="item.id"
                                            :label="item.name"
                                            :value="item.id">
                                    </el-option>
                                </el-select>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="6">
                            <el-form-item label="民族：" prop="nationId">
                                <el-select v-model="emp.nationId" size="mini" style="width: 150px" placeholder="民族">
                                    <el-option
                                            v-for="item in nations"
                                            :key="item.id"
                                            :label="item.name"
                                            :value="item.id">
                                    </el-option>
                                </el-select>
                            </el-form-item>
                        </el-col>
                        <el-col :span="5">
                            <el-form-item label="籍贯：" prop="nativePlace">
                                <el-input v-model="emp.nativePlace" placeholder="请输入籍贯" prefix-icon="el-icon-edit"
                                          size="mini" style="width: 120px"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="电子邮箱：" prop="email">
                                <el-input v-model="emp.email" placeholder="请输入电子邮箱" size="mini" style="width: 150px"
                                          prefix-icon="el-icon-message"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="联系地址：" prop="address">
                                <el-input v-model="emp.address" placeholder="请输入联系地址" size="mini" style="width: 200px"
                                          prefix-icon="el-icon-edit"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="6">
                            <el-form-item label="职位：" prop="posId">
                                <el-select v-model="emp.posId" size="mini" style="width: 150px" placeholder="职位">
                                    <el-option
                                            v-for="item in positions"
                                            :key="item.id"
                                            :label="item.name"
                                            :value="item.id">
                                    </el-option>
                                </el-select>
                            </el-form-item>
                        </el-col>
                        <el-col :span="5">
                            <el-form-item label="职称：" prop="jobLevelId">
                                <el-select v-model="emp.jobLevelId" size="mini" style="width: 150px" placeholder="职位">
                                    <el-option
                                            v-for="item in joblevels"
                                            :key="item.id"
                                            :label="item.name"
                                            :value="item.id">
                                    </el-option>
                                </el-select>
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="所属部门：" prop="departmentId">
                                <el-popover
                                        placement="bottom"
                                        title="请选择部门"
                                        width="200"
                                        trigger="manual"
                                        v-model="visible">
                                    <el-tree
                                            :data="allDeps"
                                            :props="defaultProps"
                                            default-expand-all
                                            @node-click="handleNodeClick"></el-tree>
                                    <div slot="reference"
                                         style="width: 150px;display: inline-flex;border: 1px solid #dedede;height: 24px;border-radius: 5px;cursor: pointer;align-items: center;font-size: 13px;padding-left: 8px;box-sizing: border-box"
                                         @click="showDepView">{{inputDepName}}
                                    </div>
                                </el-popover>
                            </el-form-item>
                        </el-col>
                        <el-col :span="7">
                            <el-form-item label="电话号码：" prop="phone">
                                <el-input v-model="emp.phone" placeholder="请输入电话号码" size="mini" style="width: 200px"
                                          prefix-icon="el-icon-phone"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="6">
                            <el-form-item label="工号：" prop="workID">
                                <el-input v-model="emp.workID" placeholder="请输入工号" prefix-icon="el-icon-edit" disabled
                                          size="mini" style="width: 150px"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="5">
                            <el-form-item label="学历：" prop="tiptopDegree">
                                <el-select v-model="emp.tiptopDegree" size="mini" style="width: 150px" placeholder="学历">
                                    <el-option
                                            v-for="item in tiptopDegrees"
                                            :key="item"
                                            :label="item"
                                            :value="item">
                                    </el-option>
                                </el-select>
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="毕业院校：" prop="school">
                                <el-input v-model="emp.school" placeholder="请输入学校" size="mini" style="width: 150px"
                                          prefix-icon="el-icon-edit"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="7">
                            <el-form-item label="专业名称：" prop="specialty">
                                <el-input v-model="emp.specialty" placeholder="请输入专业名称" prefix-icon="el-icon-edit"
                                          size="mini" style="width: 200px"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="6">
                            <el-form-item label="入职日期：" prop="beginDate">
                                <el-date-picker
                                        v-model="emp.beginDate"
                                        type="date"
                                        size="mini"
                                        style="width: 130px"
                                        value-format="yyyy-MM-dd"
                                        placeholder="入职日期">
                                </el-date-picker>
                            </el-form-item>
                        </el-col>
                        <el-col :span="5">
                            <el-form-item label="转正日期：" prop="conversionTime">
                                <el-date-picker
                                        v-model="emp.conversionTime"
                                        type="date"
                                        size="mini"
                                        style="width: 130px"
                                        value-format="yyyy-MM-dd"
                                        placeholder="转正日期">
                                </el-date-picker>
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="合同起始日期：" prop="beginContract">
                                <el-date-picker
                                        v-model="emp.beginContract"
                                        type="date"
                                        size="mini"
                                        style="width: 130px"
                                        value-format="yyyy-MM-dd"
                                        placeholder="合同起始日期">
                                </el-date-picker>
                            </el-form-item>
                        </el-col>
                        <el-col :span="7">
                            <el-form-item label="合同截止日期：" prop="endContract">
                                <el-date-picker
                                        v-model="emp.endContract"
                                        type="date"
                                        size="mini"
                                        style="width: 170px"
                                        value-format="yyyy-MM-dd"
                                        placeholder="合同截止日期">
                                </el-date-picker>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="8">
                            <el-form-item label="身份证号码：" prop="idCard">
                                <el-input v-model="emp.idCard" placeholder="请输入身份证号码" size="mini"
                                          prefix-icon="el-icon-edit" style="width: 180px"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="8">
                            <el-form-item label="聘用形式：" prop="engageForm">
                                <el-radio-group v-model="emp.engageForm" style="margin-top: 8px">
                                    <el-radio label="劳动合同">劳动合同</el-radio>
                                    <el-radio label="劳务合同">劳务合同</el-radio>
                                </el-radio-group>
                            </el-form-item>
                        </el-col>
                        <el-col :span="8">
                            <el-form-item label="婚姻状况：" prop="wedlock">
                                <el-radio-group v-model="emp.wedlock" style="margin-top: 8px">
                                    <el-radio label="已婚">已婚</el-radio>
                                    <el-radio label="未婚">未婚</el-radio>
                                    <el-radio label="离异">离异</el-radio>
                                </el-radio-group>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-form>
            </div>
            <span slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="doAddEmp">确 定</el-button>
          </span>
        </el-dialog>
    </div>
</template>

<script>
    export default {
        name: "EmpBasic",
        data() {
            return {
                searchValue: {
                    politicId: null,
                    nationId: null,
                    posId: null,
                    jobLevelId: null,
                    engageForm: '',
                    departmentId: null,
                    beginDateScope: null
                },
                showAdvanceSearchVisible: false,
                headers: {
                    Authorization: window.sessionStorage.getItem('tokenStr')
                },
                importDataDisabled: false,
                importDataBtnText: '导入数据',
                importDataBtnIcon: 'el-icon-upload2',
                title: '',
                defaultProps: {
                    children: 'children',
                    label: 'name'
                },
                inputDepName: '',
                allDeps: [],
                visible: false,
                visible2: false,
                emps: [],
                loading: false,
                total: 0,
                currentPage: 1,
                size: 10,
                empName: '',
                dialogVisible: false,
                nations: [],
                joblevels: [],
                politicsstatus: [],
                positions: [],
                tiptopDegrees: ['博士', '硕士', '本科', '大专', '高中', '初中', '小学', '其他'],
                emp: {
                    id: null,
                    fkComponent: '',
                    isOk: '',
                    isIdle: '',
                    user: '',
                    currentVersion: '',
                    currentServer: null,
                    commnets: '',
                    machineName: null,
                    ip: '',
                    connected: '',
                    running: '',
                    ctime: null,
                    utim: null,
                    reportAddress: null
                },
        ComponentData:[
              { label: 1, value: 'HardWare Settings' },
              { label: 5, value: 'Gaming' },
              { label: 2, value: 'ITS' },
              { label: 6, value: 'DPM' }
        ],
        danoptions: [
            [
              { label: 1, value: 'HardWare Settings' },
              { label: 5, value: 'Gaming' },
              { label: 2, value: 'ITS' },
              { label: 6, value: 'DPM' }
            ],
            [
              { label: 7, value: 'TPNDA' }
            ],
            [
              { label: 8, value: 'Utility' }
            ],
            [
              { label: 9, value: 'Lenovo Smartkey' }
            ]
          ],
          radioIndex: 0,
          componentId: 100,
                rules: {
                    name: [{required: true, message: '请输入员工姓名', trigger: 'blur'}],
                    gender: [{required: true, message: '请输入员工性别', trigger: 'blur'}],
                    birthday: [{required: true, message: '请输入生出日期', trigger: 'blur'}],
                    idCard: [{required: true, message: '请输入身份证号码', trigger: 'blur'},
                        {
                            pattern: /(^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$)|(^[1-9]\d{5}\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{2}$)/,
                            message: '身份证号码不正确',
                            trigger: 'blur'
                        }],
                    wedlock: [{required: true, message: '请输入婚姻状况', trigger: 'blur'}],
                    nationId: [{required: true, message: '请输入民族', trigger: 'blur'}],
                    nativePlace: [{required: true, message: '请输入籍贯', trigger: 'blur'}],
                    politicId: [{required: true, message: '请输入政治面貌', trigger: 'blur'}],
                    email: [{required: true, message: '请输入邮箱地址', trigger: 'blur'}, {
                        type: 'email',
                        message: '邮箱地址格式不正确',
                        trigger: 'blur'
                    }],
                    phone: [{required: true, message: '请输入电话号码', trigger: 'blur'}],
                    address: [{required: true, message: '请输入员工地址', trigger: 'blur'}],
                    departmentId: [{required: true, message: '请输入部门名称', trigger: 'blur'}],
                    jobLevelId: [{required: true, message: '请输入职称', trigger: 'blur'}],
                    posId: [{required: true, message: '请输入职位', trigger: 'blur'}],
                    engageForm: [{required: true, message: '请输入聘用形式', trigger: 'blur'}],
                    tiptopDegree: [{required: true, message: '请输入学历', trigger: 'blur'}],
                    specialty: [{required: true, message: '请输入专业', trigger: 'blur'}],
                    school: [{required: true, message: '请输入毕业院校', trigger: 'blur'}],
                    beginDate: [{required: true, message: '请输入入职日期', trigger: 'blur'}],
                    workState: [{required: true, message: '请输入工作状态', trigger: 'blur'}],
                    workID: [{required: true, message: '请输入工号', trigger: 'blur'}],
                    contractTerm: [{required: true, message: '请输入合同期限', trigger: 'blur'}],
                    conversionTime: [{required: true, message: '请输入转正日期', trigger: 'blur'}],
                    notWorkDate: [{required: true, message: '请输入离职日期', trigger: 'blur'}],
                    beginContract: [{required: true, message: '请输入合同起始日期', trigger: 'blur'}],
                    endContract: [{required: true, message: '请输入合同结束日期', trigger: 'blur'}],
                    workAge: [{required: true, message: '请输入工龄', trigger: 'blur'}],
                }
            }
        },
        mounted() {
            this.initEmps();
            // this.initData();
            // this.initPositions();
        },
        methods: {
            onSuccess() {
                this.importDataBtnIcon = 'el-icon-upload2';
                this.importDataBtnText = '导入数据';
                this.importDataDisabled = false;
                this.initEmps();
            },
            onError() {
                this.importDataBtnIcon = 'el-icon-upload2';
                this.importDataBtnText = '导入数据';
                this.importDataDisabled = false;
            },
            beforeUpload() {
                this.importDataBtnIcon = 'el-icon-loading';
                this.importDataBtnText = '正在导入';
                this.importDataDisabled = true;
            },
            exportData() {
                // this.downloadRequest('/employee/basic/export');
                this.putRequest('/component/basic/getComponents').then(resp => {
                    console.log(resp)
                })
            },
            showEditEmpView(data) {
                this.title = '编辑员工信息';
                this.emp = data;
                this.inputDepName = data.department.name;
                this.initPositions();
                this.dialogVisible = true;
            },
            deleteEmp(data) {
                this.$confirm('此操作将永久删除 ' + data.name + ', 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    // this.deleteRequest('/employee/basic/' + data.id).then(resp => {
                    //     if (resp) {
                    //         this.initEmps();
                    //     }
                    // })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            },
            doAddEmp() {
                if (this.emp.id) {
                    this.$refs['empForm'].validate(valid => {
                        if (valid) {
                            // this.putRequest('/employee/basic/', this.emp).then(resp => {
                            //     if (resp) {
                            //         this.dialogVisible = false;
                            //         this.initEmps();
                            //     }
                            // })
                        }
                    })
                } else {
                    this.$refs['empForm'].validate(valid => {
                        if (valid) {
                            // this.postRequest('/employee/basic/', this.emp).then(resp => {
                            //     if (resp) {
                            //         this.dialogVisible = false;
                            //         this.initEmps();
                            //     }
                            // })
                        }
                    })
                }
            },
            searchHandleNodeClick(data) {
                this.inputDepName = data.name;
                this.searchValue.departmentId = data.id;
                this.visible2 = !this.visible2;
            },
            handleNodeClick(data) {
                this.inputDepName = data.name
                this.emp.departmentId = data.id;
                this.visible = !this.visible;
            },
            showDepView2() {
                this.visible2 = !this.visible2;
            },
            showDepView() {
                this.visible = !this.visible;
            },
            getMaxWorkID() {
                // this.getRequest('/employee/basic/maxWorkID').then(resp => {
                //     if (resp) {
                //         this.emp.workID = resp.obj;
                //     }
                // })
            },
            initPositions() {
                // this.getRequest('/employee/basic/positions').then(resp => {
                //     if (resp) {
                //         this.positions = resp;
                //     }
                // })
            },
            initData() {
                this.getRequest('/component/basic/selectComponentMachine?id=1').then(resp => {
                    this.emps = resp
                })
                if (!window.sessionStorage.getItem('nations')) {
                    // this.getRequest('/employee/basic/nations').then(resp => {
                    //     if (resp) {
                    //         this.nations = resp;
                    //         window.sessionStorage.setItem('nations', JSON.stringify(resp));
                    //     }
                    // })
                } else {
                    this.nations = JSON.parse(window.sessionStorage.getItem('nations'));
                }
                if (!window.sessionStorage.getItem('joblevels')) {
                    // this.getRequest('/employee/basic/joblevels').then(resp => {
                    //     if (resp) {
                    //         this.joblevels = resp;
                    //         window.sessionStorage.setItem('joblevels', JSON.stringify(resp));
                    //     }
                    // })
                } else {
                    this.joblevels = JSON.parse(window.sessionStorage.getItem('joblevels'));
                }
                if (!window.sessionStorage.getItem('politicsstatus')) {
                    // this.getRequest('/employee/basic/politicsstatus').then(resp => {
                    //     if (resp) {
                    //         this.politicsstatus = resp;
                    //         window.sessionStorage.setItem('politicsstatus', JSON.stringify(resp));
                    //     }
                    // })
                } else {
                    this.politicsstatus = JSON.parse(window.sessionStorage.getItem('politicsstatus'));
                }
                if (!window.sessionStorage.getItem('allDeps')) {
                    // this.getRequest('/employee/basic/deps').then(resp => {
                    //     if (resp) {
                    //         this.allDeps = resp;
                    //         window.sessionStorage.setItem('allDeps', JSON.stringify(resp));
                    //     }
                    // });
                } else {
                    this.allDeps = JSON.parse(window.sessionStorage.getItem('allDeps'));
                }
            },
            sizeChange(size) {
                this.size = size;
                this.initEmps();
            },
            currentChange(currentPage) {
                this.currentPage = currentPage;
                this.initEmps();
            },
            showAddEmpView() {
                this.title = '添加机器';
                this.emp = {
                    id: null,
                    name: '',
                    ip: '',
                    fkComponent: '',
                    user: ''
                };
                this.inputDepName = '';
                this.getMaxWorkID();
                this.initPositions();
                this.dialogVisible = true;
            },
            onEventClick(event) {
                if (this.machines.length === 0) {
                this.runSelectionMessage()
                return null
            }
            if (this.runMachines.length === 0) {
                this.warningMessage()
                return null
            }
            this.runMachinesType.runType = event.target.innerText
            this.runMachinesType.machines = this.runMachines
            console.log(event.target.innerText)
            this.historyLoading = true
            const requestUrl = 'autotest/runMachinesType/?'
            this.getRequest(requestUrl)
            this.$store
                .dispatch('autotest/runMachinesType', this.runMachinesType)
                .then(data => {
                this.tableDatas = data
                this.historyLoading = false
                console.log(this.tableDatas)
                }).catch(err => {
                console.log(err)
                })
            },
            initEmps() {
                this.loading = true;
                let url = '/component/basic/?currentPage=' + this.currentPage + '&size=' + this.size;
                // if (type && type == 'advanced') {
                //     if (this.searchValue.politicId) {
                //         url += '&politicId=' + this.searchValue.politicId;
                //     }
                //     if (this.searchValue.nationId) {
                //         url += '&nationId=' + this.searchValue.nationId;
                //     }
                //     if (this.searchValue.posId) {
                //         url += '&posId=' + this.searchValue.posId;
                //     }
                //     if (this.searchValue.jobLevelId) {
                //         url += '&jobLevelId=' + this.searchValue.jobLevelId;
                //     }
                //     if (this.searchValue.engageForm) {
                //         url += '&engageForm=' + this.searchValue.engageForm;
                //     }
                //     if (this.searchValue.departmentId) {
                //         url += '&departmentId=' + this.searchValue.departmentId;
                //     }
                //     if (this.searchValue.beginDateScope) {
                //         url += '&beginDateScope=' + this.searchValue.beginDateScope;
                //     }
                // } else {
                //     url += '&name=' + this.empName;
                // }
                this.getRequest(url).then(resp => {
                    this.loading = false;
                    if (resp) {
                        this.emps = resp.data;
                        this.total = resp.total
                    }
                })
            },
        handleClick(tab, event) {
      console.log(tab, event)
      switch (tab.name) {
          case 'Vantage':
            this.radioIndex = 0
            this.componentId = 100
            this.getChanged()
            break
          case 'TPNDA':
            this.radioIndex = 1
            this.componentId = 7
            this.getChanged()
            break
          case 'Utility':
            this.radioIndex = 2
            this.componentId = 8
            this.getChanged()
            break
          default:
            this.radioIndex = 3
            this.componentId = 9
            this.getChanged()
        }
    }
        }
    }
</script>

<style>
    /* 可以设置不同的进入和离开动画 */
    /* 设置持续时间和动画函数 */
    .slide-fade-enter-active {
        transition: all .8s ease;
    }

    .slide-fade-leave-active {
        transition: all .8s cubic-bezier(1.0, 0.5, 0.8, 1.0);
    }

    .slide-fade-enter, .slide-fade-leave-to
        /* .slide-fade-leave-active for below version 2.1.8 */
    {
        transform: translateX(10px);
        opacity: 0;
    }
</style>