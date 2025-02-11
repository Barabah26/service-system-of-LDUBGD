const BASE_URL = 'http://localhost:8080';

const API_ENDPOINTS = {
  STATEMENTS: {
    FIND_BY_FULL_NAME: `${BASE_URL}/statements/findByFullName`,
    STATUS_AND_FACULTY: `${BASE_URL}/statements/statusAndFaculty`,
    IN_PROGRESS: (id) => `${BASE_URL}/statements/${id}/in-progress`,
    READY: (id) => `${BASE_URL}/statements/${id}/ready`,
    CREATE_STATEMENT: `${BASE_URL}/statements/createStatement`,
  },

  FORGOT_PASSWORD: {
    FIND_BY_USER_ID: `${BASE_URL}/forgot-password/findByUserId`,
    CREATE_STATEMENT: `${BASE_URL}/forgot-password/createForgotPasswordStatement`,
    STATUS_AND_FACULTY: `${BASE_URL}/forgot-password/statusAndFaculty`,
    IN_PROGRESS: (id) => `${BASE_URL}/forgot-password/${id}/in-progress`,
    READY: (id) => `${BASE_URL}/forgot-password/${id}/ready`,
    SEND_DATA:  (statementId) => `${BASE_URL}/forgot-password/${statementId}`,
    STATUS: `${BASE_URL}/forgot-password/status`,


  },
  AUTH: {
    PROFILE: `${BASE_URL}/auth/profile`,
    AUTH: `${BASE_URL}/auth`,
    REGISTER: `${BASE_URL}/auth/register`,
    ALL_ADMINS: `${BASE_URL}/auth/admin/allAdmins`,
    ALL_USERS: `${BASE_URL}/auth/admin/allUsers`,
    REGISTER_ADMIN: `${BASE_URL}/auth/admin/register`,
    DELETE_BY_LOGIN: (login) => `${BASE_URL}/auth/admin/deleteByLogin/${login}`,
    DELETE_USER_BY_LOGIN: (login) => `${BASE_URL}/auth/admin/deleteUserByLogin/${login}`,
    UPDATE_BY_LOGIN: (login) => `${BASE_URL}/auth/admin/updateByLogin/${login}`,
    UPDATE_USER_PASSWORD_BY_LOGIN: (login) => `${BASE_URL}/auth/admin/updateUserPasswordByLogin/${login}`,

  },

  NOTIFICATION: {
    READ: (notificationId) => `${BASE_URL}/notifications/${notificationId}/read`,
    FIND_BY_USER_ID: (userId) => `${BASE_URL}/notifications?userId=${userId}`,
  },

  FILE: {
    UPLOAD: `${BASE_URL}/file/upload`,

  }
};

export default API_ENDPOINTS;
