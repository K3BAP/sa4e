// src/router/index.js
import { createRouter, createWebHistory } from "vue-router";
import SubmitWishlist from "../views/SubmitWishlist.vue";
import ElvesDashboard from "../views/ElvesDashboard.vue";
import SantaDashboard from "../views/SantaDashboard.vue";
import SuccessPage from "@/views/SuccessPage.vue";

const routes = [
  {
    path: "/",
    name: "SubmitWishlist",
    component: SubmitWishlist,
  },
  {
    path: "/elves",
    name: "ElvesDashboard",
    component: ElvesDashboard,
  },
  {
    path: "/santa",
    name: "SantaDashboard",
    component: SantaDashboard,
  },
  {
    path: "/success",
    name: "Success",
    component: SuccessPage,
  }
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
