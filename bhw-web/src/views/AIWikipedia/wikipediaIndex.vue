<template>
    <div>
        <div class="seachWrap">
            <img src="../../images/img-wikipedia_banner.jpeg" style="width: 100%;" alt="">
            <div class="seachBox">
                <el-autocomplete
                        v-model="state" style="width: 100%;"
                        :fetch-suggestions="querySearchAsync"
                        @select="handleSelect"
                        placeholder="请输入你想检索的内容">
                    <i class="el-input__icon" slot="suffix">搜 索</i>
                </el-autocomplete>
            </div>
        </div>
    </div>
</template>

<script>
    import {qryServiceDwList} from "../../api/projectDw/projectDw";
    export default {
        name: "wikipediaIndex",
        data(){
            return{
                restaurants: [],
                state: '',
                timeout:  null,
                dataType:{1:"知识产权",16:"技术供需",8:"技术合同登记",9:"技术成果认定登记",11:"法律服务",14:"财税服务"},
                lastQueryString:''
            }
        },
        methods:{
            querySearchAsync(queryString, cb) {
                if(queryString.trim()){
                    this.lastQueryString = queryString;
                    this.restaurants=[];
                    qryServiceDwList({title:queryString,auditStatus:null,pageSize:20}).then(response=>{
                        response.data.forEach(item=>{
                            var vl = {value:this.dataType[item.dataType]+'-'+item.title+'('+(item.needType==1?'供':'需')+')',
                                id:item.id,dataType:item.dataType,needType:item.needType
                            };
                            this.restaurants.push(vl);
                        })
                        cb(this.restaurants);
                    });
                }
            },
            handleSelect(item) {
                if(item.needType==1 && item.dataType==1){
                    this.$router.push({path:"/kjsc/detail",query:{productId:item.id}});
                }else if(item.needType==2){
                    this.$router.push({path:"/kjsc",query:{iconToggleFlag:1,kjscPageType:2,queryParam:this.lastQueryString}});
                }else if(item.needType==1 && item.dataType>1 && item.dataType<16){
                    this.$router.push({path:"/kjsc",query:{chengguoActiveName:1,aiServiceDetFlag:1,queryParam:this.lastQueryString}});
                }else if(item.needType==1 && item.dataType==16){
                    this.$router.push({path:"/kjsc",query:{iconToggleFlag:1,kjscPageType:1,queryParam:this.lastQueryString}});
                }
            }
        }
    }
</script>

<style scoped>
    .seachWrap{position: relative;padding-bottom: 100px;}
    .seachBox{width: 680px;position: absolute;top: 40%;left: 50%;transform: translate(-50%,-50%);-webkit-transform: translate(-50%,-50%);}
    /deep/ .el-input__inner{height: 53px;line-height: 53px;border:2px solid rgba(244,140,78,1);border-radius: 10px;}
    .seachBox /deep/ .el-input__suffix{color:#FFF;right: 0;}
    .seachBox /deep/ .el-input__icon{width: 105px;background: linear-gradient(0deg,rgba(247,74,13,1) 0%,rgba(250,150,59,1) 100%);color: #FFF;font-size: 18px;font-style: normal; line-height: 53px;display: inline-block;border-top-right-radius: 10px;border-bottom-right-radius: 10px;}
</style>