<template>
  <!-- Header -->
  <HeaderCom />
  <!-- 본문 -->
  <router-view/>

  <!-- Footer -->
  <FooterCom />
</template>

<script>
import HeaderCom from "./components/common/HeaderCom.vue";
import FooterCom from "./components/common/FooterCom.vue";

export default {
  components: {
    HeaderCom,
    FooterCom
  }
}
</script>

<style>
</style>
