<template>
	<div class="layout">
		<el-container>
			<el-aside style="width: auto;"><Navbar :routes="permissionMenus"></Navbar></el-aside>
			<el-main style="background: rgb(236,236,236);">
				<Topbar></Topbar>
				<AppMain></AppMain>
			</el-main>
		</el-container>
	</div>
</template>

<script>
import { mapGetters } from "vuex";
import Navbar from '../../views/layout/Navbar'
import AppMain from '../../views/layout/AppMain'
import Topbar from "../../views/layout/Topbar";
export default {
	name: 'layout',
  	components: {
		Navbar,
		AppMain,Topbar
  	},
    computed:{
        ...mapGetters(["permissionMenus"])
    }
  /*data () {
    return {
      permissionMenus: [
        {
          children: [],
          code: 'home',
          href: '/home',
          icon: 'fa-user',
          iconClass: 'icon-home',
          id: 1,
          parentId: -1,
          path: '/home',
          spread: false,
          title: '首页',
          type: 'dirt'
		},{
      		children: [{
				children: [],
				code: 'tj',
				href: '/about',
				icon: 'fa-user',
				iconClass: 'icon-home',
				id: 21,
				parentId: -1,
				spread: false,
				title: '统计页面',
				type: 'dirt'
			},{
				children: [],
				code: 'ger',
				href: '/about',
				icon: 'fa-user',
				iconClass: 'icon-home',
				id: 22,
				parentId: -1,
				spread: false,
				title: '个人用户管理',
				type: 'dirt'
			},{
				children: [],
				code: 'qiy',
				href: '/about',
				icon: 'fa-user',
				iconClass: 'icon-home',
				id: 23,
				parentId: -1,
				spread: false,
				title: '企业用户管理',
				type: 'dirt'
			},{
				children: [],
				code: 'saleData',
				href: '/about',
				icon: 'fa-user',
				iconClass: 'icon-home',
				id: 24,
				parentId: -1,
				spread: false,
				title: '销售数据编辑',
				type: 'dirt'
			},{
				children: [],
				code: 'VIPUser',
				href: '/about',
				icon: 'fa-user',
				iconClass: 'icon-home',
				id: 25,
				parentId: -1,
				spread: false,
				title: 'VIP 管理列表',
				type: 'dirt'
			}],
			code: 'user',
			  href: '/home',
			  icon: 'icon-yonghu',
			  iconClass: 'icon-yonghu',
			  id: 2,
			  parentId: -1,
			  path: '/home',
			  spread: false,
			  title: '用户管理',
			  type: 'dirt'
		  },{
			  children: [{
				  children: [],
				  code: 'order',
				  href: '/about',
				  icon: 'fa-user',
				  iconClass: 'icon-home',
				  id: 31,
				  parentId: -1,
				  spread: false,
				  title: '订单列表',
				  type: 'dirt'
			  }],
			  code: 'financial',
			  href: '/home',
			  icon: 'icon-caiwu',
			  iconClass: 'icon-caiwu',
			  id: 3,
			  parentId: -1,
			  path: '/home',
			  spread: false,
			  title: '财务管理',
			  type: 'dirt'
		  },{
			  children: [],
			  code: 'information',
			  href: '/home',
			  icon: 'icon-xinxi',
			  iconClass: 'icon-xinxi',
			  id: 4,
			  parentId: -1,
			  path: '/home',
			  spread: false,
			  title: '信息管理',
			  type: 'dirt'
		  },{
			  children: [],
			  code: 'system',
			  href: '/home',
			  icon: 'icon-system',
			  iconClass: 'icon-system',
			  id: 5,
			  parentId: -1,
			  path: '/home',
			  spread: false,
			  title: '系统管理',
			  type: 'dirt'
		  }]
    }
  }*/
}
</script>
<style scoped>
	/deep/ .el-main{padding: 0;}
</style>