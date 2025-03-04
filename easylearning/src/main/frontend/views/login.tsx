// frontend/views/login.tsx
import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import { useSignal } from '@vaadin/hilla-react-signals';
import { LoginI18n, LoginOverlay, LoginOverlayElement } from '@vaadin/react-components';
import { useAuth } from 'Frontend/util/auth.js';
import { useState } from 'react';
import { Button } from '@vaadin/react-components/Button.js';

export const config: ViewConfig = {
  menu: { exclude: true },
  flowLayout: false,
};

const loginI18n: LoginI18n = {
  ...new LoginOverlayElement().i18n,
  header: {
    title: 'easyLearning',
    description: 'Login using: user/1234 or admin/1234',
  },
};

export default function LoginView() {
  const { login } = useAuth();
  const loginError = useSignal(false);
  const [opened, setOpened] = useState(true); // Control overlay visibility

  const handleCancel = () => {
    setOpened(false); // Close the overlay
    document.location = '/home'; // Redirect using document.location
  };

  return (
    <LoginOverlay
      opened={opened}
      error={loginError.value}
      noForgotPassword={false}
      i18n={loginI18n}
      onLogin={async ({ detail: { username, password } }) => {
        const { defaultUrl, error, redirectUrl } = await login(username, password);
        if (error) {
          loginError.value = true;
        } else {
          const url = redirectUrl ?? defaultUrl ?? '/home';
          const path = new URL(url, document.baseURI).pathname;
          document.location = path;
        }
      }}
      // onOpenedChanged={(e) => setOpened(e.detail.value)} // Sync overlay state
    >
      {/* Centered Cancel Button */}
      <div
        slot="footer"
        style={{
          display: 'flex',
          justifyContent: 'center',
          width: '100%',
        }}
      >
        <Button onClick={handleCancel} theme="tertiary">
          Cancel
        </Button>
      </div>
    </LoginOverlay>
  );
}