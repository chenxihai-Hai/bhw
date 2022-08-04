<template>
  <div id="app">
    <!--<div id="nav">
      <router-link to="/">Home</router-link> |
      <router-link to="/about">About</router-link>
    </div>-->
    <router-view></router-view>
  </div>
</template>
<!--全局样式引入-->
<style lang="css">
  @import "./style/index.css";
</style>
<style lang="scss">
#app {
  font-family: 'SimSun',"Avenir", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}
#nav {
  a {
    font-weight:normal;
    color: #2c3e50;

    &.router-link-exact-active {
      color: #42b983;
    }
  }
}
</style>
