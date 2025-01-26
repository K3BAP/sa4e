<template>
  <v-app>
    <v-app-bar app>
      <v-app-bar-nav-icon @click.stop="drawer = !drawer"></v-app-bar-nav-icon>
      <v-toolbar-title>
        XMasWishes
      </v-toolbar-title>
    </v-app-bar>

    <v-navigation-drawer app v-model="drawer" temporary>
      <v-list>
        <v-list-item
          v-for="(item, index) in menuItems"
          :key="index"
          @click="navigate(item.route)"
        >
          <v-list-item-title>{{ item.title }}</v-list-item-title>
        </v-list-item>
      </v-list>
    </v-navigation-drawer>

    <v-main>
      <router-view />
    </v-main>
  </v-app>
</template>

<script>
import { useRouter } from "vue-router";

export default {
  setup() {
    const router = useRouter();

    const navigate = (route) => {
      router.push(route);
    };

    return {
      
      navigate,
    };
  },
  data() {
    return {
      drawer: false,
      menuItems: [
        { title: "Submit Wishlist", route: "/" },
        { title: "Elves Dashboard", route: "/elves" },
        { title: "Santa's Dashboard", route: "/santa" },
      ],
    };
  },
};
</script>

<style>
@import "~vuetify/dist/vuetify.min.css";
</style>
