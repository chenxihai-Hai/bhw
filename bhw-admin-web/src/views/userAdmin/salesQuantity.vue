<template>
    <div style="padding:22px 16px;">
        <div class="salesQuantity">
            <el-form :inline="true" :model="formInline" class="demo-form-inline">
                <el-form-item label="ID">
                    <el-input v-model="formInline.id" placeholder="请输入"></el-input>
                </el-form-item>
                <el-form-item label="用户名">
                    <el-input v-model="formInline.name" placeholder="请输入"></el-input>
                </el-form-item>
                <el-form-item label="产品/服务类型">
                    <el-select v-model="formInline.type" placeholder="技术合同登记" class="selected">
                        <el-option label="区域一" value="shanghai"></el-option>
                        <el-option label="区域二" value="beijing"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="onSubmit">查询</el-button>
                </el-form-item>
            </el-form>
            <el-button type="warning" class="btn">删除</el-button>
            <el-button type="primary" class="btn" @click="insert">添加条件</el-button>
            <el-main>
                <el-table :data="tableData" border style="width: 100%" class="allTable">
                    <el-table-column
                            type="selection"
                            width="56">
                    </el-table-column>
                    <el-table-column type="index" label="ID" width="66">
                    </el-table-column>
                    <el-table-column
                            prop="name"
                            label="用户名"
                            width="137">
                    </el-table-column>
                    <el-table-column label="产品/服务类型" width="295">
                        <template slot-scope="scope">{{scope.row.type==1?'技术合同登记':'商户用户'}}</template>
                    </el-table-column>
                    <el-table-column label="修改成交量" width="277">
                        <template slot-scope="scope">
                            <span style="margin-right:10px">{{scope.row.num}}</span>
                            <el-link type="primary" href="">修改</el-link>
                        </template>
                    </el-table-column>
                    <el-table-column prop="time" label="修改时间">
                    </el-table-column>
                </el-table>
            </el-main>
            <div class="block">
                <el-pagination
                        @size-change="handleSizeChange"
                        @current-change="handleCurrentChange"
                        :current-page.sync="currentPage3"
                        :page-size="100"
                        layout="prev, pager, next, jumper"
                        :total="1000">
                </el-pagination>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "salesQuantity",
        data() {
            return {
                formInline: {
                    id: '',
                    name: '',
                    type: '',
                },
                tableData: [
                    {
                        id: "001",
                        name: "黄惠丽",
                        type: '1',
                        num: '666888',
                        time: '2020·03·24·14：30-2030·03·24·14：30',
                    }
                ],
            }
        },
        methods: {
            handleSizeChange(val) {
                console.log(`每页 ${val} 条`);
            },
            handleCurrentChange(val) {
                console.log(`当前页: ${val}`);
            },
            handleSelectionChange(val) {
                this.multipleSelection = val;
            },
        }
    }
</script>

<style scoped>
    .salesQuantity {
        background-color: white;
    }

    .demo-form-inline {
        text-align: center;
        padding-top: 50px;
    }

    .btn {
        margin: 50px 0px 20px 30px;
    }

    .block {
        text-align: center;
    }

    .selected {
        width: 150px;
    }
</style>