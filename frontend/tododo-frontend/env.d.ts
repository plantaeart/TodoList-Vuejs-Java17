declare module "app-env" {
  interface ENV {
    VITE_API_BASE_URL: string;
    VITE_DEBUG: boolean;
    VITE_IS_TEST: boolean;
  }

  const appEnv: ENV;
  export default appEnv;
}
