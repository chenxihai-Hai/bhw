<template>
	<section class="appMain" style="height: calc(100% - 90px);">
		<transition name="fade" mode="out-in">
			<router-view :key="key"></router-view>
		</transition>
	</section>

</template>

<script>
    import {getLanguageConfig} from '../../api/admin/index'
	export default {
 		name: 'appMain',
  		computed: {
   		 	key() {
    			return this.$route.name !== undefined ? this.$route.name + +new Date() : this.$route + +new Date()
   			 }
		},
		created(){
            /*getLanguageConfig().then(response=>{
                var json = JSON.parse(response.data);
                for(var j in json){
                    this.$i18n.mergeLocaleMessage(j, json[j]);
				}
                this.$i18n.locale = 'cn';
				console.log('$i18n.t("home")',this.$i18n.t("home"));
			});*/
		}
	}
</script>
