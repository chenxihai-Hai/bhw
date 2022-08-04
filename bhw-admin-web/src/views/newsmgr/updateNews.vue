<template>
    <el-container>
      <el-header>
        <h1>修改信息</h1>
      </el-header>
      <el-main>
        <el-form :model="updateData"  label-width="80px" class="demo-ruleForm" style="width: 700px;margin:0 auto">
          <el-form-item label="标题" >
            <el-input v-model="updateData.title"></el-input>
          </el-form-item>
          <el-form-item label="类型" >
            <el-input v-model="updateData.type"></el-input>
          </el-form-item>
          <el-form-item label="图片">
            <el-upload
              action="https://jsonplaceholder.typicode.com/posts/"
              list-type="picture-card"
              :limit="1"
              :file-list="fileList">
              <i class="el-icon-plus"></i>
            </el-upload>
          </el-form-item>
          <el-form-item label="链接" >
            <el-input v-model="updateData.href"></el-input>
          </el-form-item>
          <el-form-item label="内容" >
            <el-input v-model="updateData.content" type="textarea"></el-input>
          </el-form-item>
          <el-form-item size="large">
            <el-button type="primary" @click="submitForm('updateData')">更新</el-button>
            <el-button @click="resetForm()">返回</el-button>
          </el-form-item>
        </el-form>
      </el-main>
    </el-container>
</template>

<script>
    import ElContainer from "element-ui/packages/container/src/main";
    import ElMain from "element-ui/packages/main/src/main";
    import ElHeader from "element-ui/packages/header/src/main";

    export default {
      components: {
        ElHeader,
        ElMain,
        ElContainer
      },
      name: "update-news",
      data() {
        return {
          updateData: {
            title: this.$route.params.title,
            type: this.$route.params.type,
            href: this.$route.params.href,
            content: this.$route.params.content
          },
          fileList:[{name:this.$route.params.pictureName,url:this.$route.params.picturePath}]
        }
      },
      methods: {
        submitForm(formName) {
        },
        resetForm() {
          this.$router.push("select-news");
        }
      },mounted() {

      }
    }
</script>

<style scoped>

</style>
