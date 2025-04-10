interface ImportMetaEnv {
  readonly VITE_API_BASE_URL: string
  readonly VITE_DEBUG: boolean
  readonly VITE_IS_TEST: boolean
}

interface ImportMeta {
  readonly env: ImportMetaEnv
}
