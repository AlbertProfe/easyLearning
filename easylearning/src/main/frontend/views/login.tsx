import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import { useSignal } from '@vaadin/hilla-react-signals';
import { LoginI18n, LoginOverlay, LoginOverlayElement } from '@vaadin/react-components';
import { useAuth } from 'Frontend/util/auth.js';

export const config: ViewConfig = {
  menu: { exclude: true },
  flowLayout: false,
};

const loginI18n: LoginI18n = {
  ...new LoginOverlayElement().i18n,
  header: { title: 'Hilla Auth Starter', description: 'Login using user/1234 or admin/admin' },
};

export default function LoginView() {
  const { login } = useAuth();
  const loginError = useSignal(false);

  return (
    <LoginOverlay
      opened
      error={loginError.value}
      noForgotPassword
      i18n={loginI18n}
      onErrorChanged={(event) => {
        loginError.value = event.detail.value;
      }}
      onLogin={async ({ detail: { username, password } }) => {
        const { defaultUrl, error, redirectUrl } = await login(username, password);

        if (error) {
          loginError.value = true;
        } else {
          const url = redirectUrl ?? defaultUrl ?? '/';
          const path = new URL(url, document.baseURI).pathname;
          document.location = path;
        }
      }}
    />
  );
}
