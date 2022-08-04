<template>
  <div id="app">
    <!--<div id="nav">
      <router-link to="/">{{$t('message.home')}}</router-link> |
      <router-link to="/about">About</router-link>
    </div>-->
    <router-view />
  </div>
</template>
<!--全局样式引入-->
<style lang="css">
  @import "./styles/index.css";
</style>
<style>
#app {
  font-family: Microsoft YaHei;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  /*text-align: right;*/
  color: #2c3e50;
}

#nav {
  padding: 30px;

  a {
    font-weight: normal;
    color: #2c3e50;

    &.router-link-exact-active {
      color: #42b983;
    }
  }
}
</style>
