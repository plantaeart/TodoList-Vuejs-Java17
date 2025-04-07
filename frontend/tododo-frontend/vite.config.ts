import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import tailwindcss from '@tailwindcss/vite'
import vue from '@vitejs/plugin-vue'
import vueDevTools from 'vite-plugin-vue-devtools'
import EnvCaster from '@niku/vite-env-caster'

// https://vite.dev/config/
export default defineConfig({
  envDir: './env',
  plugins: [vue(), vueDevTools(), tailwindcss(), EnvCaster()],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url)),
    },
  },
  server: {
    proxy: {
      '^/api': {
        target: 'http://localhost:8080', // Spring Boot API URL
        ws: true,
        changeOrigin: true,
      },
    },
  },
})
