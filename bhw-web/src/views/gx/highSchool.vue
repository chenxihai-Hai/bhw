<template>
    <div style="margin-bottom: 100px;">
        <el-tabs v-model="activeName" @tab-click="handleClick" class="gxfl">
            <el-tab-pane label="国内高校" name="1"></el-tab-pane>
            <el-tab-pane label="国外高校" name="0"></el-tab-pane>
        </el-tabs>
        <div >
            <el-row type="flex" justify="center">
                <el-col style="width: 1240px;margin: 0 auto;">
                    <el-row>
                        <el-col v-for="school in schoolList" :key="school.id" class="schoolInfo">
                                <div class="img">
                                    <img :src="school.collegePhoto" style="width: 130px;height: 130px"/>
                                </div>
                                <div class="name">{{school.collegeName}}</div>
                                <div class="xq">
                                    <div class="dizhi">
                                        <img src="../../images/school/dizhi.png">
                                    </div>
                                    <span class="address" v-bind:title="school.collegeProvince +' '+ school.collegeCity">{{school.collegeProvince}}&nbsp;{{school.collegeCity}}</span>
                                    <el-button type class="xqbutton" @click="showSchoolDet(school)">详情</el-button>
                                </div>
                                <div></div>
                        </el-col>
                    </el-row>
                </el-col>
            </el-row>
        </div>
    </div>
</template>
<script>
    import {getCollege} from "../../api/school/school";
    export default {
        name: "highSchool",
        data() {
            return {
                activeName: '1',
                src: "",
                schoolList: [],
                queryParam:{
                    type:"1",
                    pageSize:"16",
                    curPage:"1"
                }
            };
        },
        created() {
            this.loadSchoolList();
        },
        methods: {
            iconToggle(val){
                this.iconToggleFlag = val;
            },

            handleClick(tab) {
                this.queryParam.type=tab.name;
                this.loadSchoolList();
            },
            loadSchoolList() {
                getCollege(this.queryParam).then(response => {
                    this.schoolList = response.data;
                });
            },
            showSchoolDet(schoolItem) {
                this.$router.push({path:'/gx/detail',query: {sItem:schoolItem}})
            }
        }
    }
</script>

<style scoped>
    /deep/ .el-tabs__item.is-active{background:none; color:#1d1d1d;}
</style>