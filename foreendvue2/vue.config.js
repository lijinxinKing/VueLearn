const port = process.env.port || process.env.npm_config_port || 8089 // dev port

// All configuration item explanations can be find in https://cli.vuejs.org/config/
module.exports = {

  devServer: {
    port: port,
    open: true,
    overlay: {
      warnings: false,
      errors: true
    },
    proxy: {
      [process.env.VUE_APP_BASE_API]: {
        target: `http://localhost:8081`,
        changeOrigin: true,
        pathRewrite: {
          ['^' + process.env.VUE_APP_BASE_API]: ''
        }
      }
    }
  }
}