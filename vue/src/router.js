import { createWebHistory, createRouter } from "vue-router";
import Main from "@/views/main.vue";

const routes = [
    {
        path: "/",
        component: Main
    },
]

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;

