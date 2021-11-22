<template>
    <div class="home">
        <img alt="vue logo" src="../assets/logo.png">
        <HelloWorld msg="Welcomw to Your "></HelloWorld>
    </div>
</template>
<script>
// @ is an alias to /src
import HelloWorld from '@/components/HelloWorld.vue'
export default {
  name: 'Home',
  components: {
    HelloWorld
  }
}
</script>
