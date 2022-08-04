<template>
	<div class="layout">
		<TopBar></TopBar>
		<Navbar :routes="permissionMenus" ></Navbar>
		<el-main><AppMain ></AppMain></el-main>
		<FootBar></FootBar>
	</div>
</template>

<script>
	import { mapGetters } from "vuex";
	import Navbar from "../../views/layout/Navbar";
	import AppMain from "../../views/layout/AppMain";
	import TopBar from "../../views/layout/TopBar";
	import FootBar from "../../views/layout/FootBar";
export default {
  	name: "layout",
    components: {
		FootBar,
        Navbar,
		AppMain,
		TopBar,
		FootBar
    },
	computed:{
        ...mapGetters(["permissionMenus"])
	}
};
</script>

