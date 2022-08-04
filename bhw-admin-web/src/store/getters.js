const getters = {
  token: state => state.user.token,
  userName: state => state.user.user.userName,
    backUserId: state => state.user.user.id,
  roles: state => state.user.roles,
  permissionMenus: state => state.user.permissionMenus,
};
export default getters
