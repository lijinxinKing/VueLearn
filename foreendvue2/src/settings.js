module.exports = {
  // 设置标题
  title: 'Lenovo Beijing APP Automation',

  /**
   * @type {boolean} true | false
   * @description Whether show the settings right-panel
   */
  // 页面设置的开关
  showSettings: false,

  /**
   * @type {boolean} true | false
   * @description Whether need tagsView
   */
  // kc: 关闭上面的tab也菜单
  tagsView: false,

  /**
   * @type {boolean} true | false
   * @description Whether fix the header
   */
  fixedHeader: false,

  /**
   * @type {boolean} true | false
   * @description Whether show the logo in sidebar
   */
  // kc: 设置logo
  sidebarLogo: true,

  /**
   * @type {string | array} 'production' | ['production', 'development']
   * @description Need show err logs component.
   * The default is only used in the production env
   * If you want to also use it in dev, you can pass ['production', 'development']
   */
  errorLog: 'production'
}
