import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import { useSignal } from '@vaadin/hilla-react-signals';
import { LoginI18n, LoginOverlay, LoginOverlayElement } from '@vaadin/react-components';
import { useAuth } from 'Frontend/util/auth.js';
import { useState } from 'react';
import { Button } from '@vaadin/react-components/Button.js';
import { useNavigate } from 'react-router-dom'; // Import useNavigate for redirection


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
  const navigate = useNavigate(); // Use useNavigate for redirection

  const handleCancel = () => {
    setOpened(false); // Close the overlay
    //navigate('/home'); // Redirect to the register page using react-router-dom
    document.location = '/home';
  };

  return (
    <LoginOverlay
      opened={opened}
      error={loginError.value}
      ForgotPassword
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
    >

      {/* Centered Cancel Button using Flexbox */}
            <div
              slot="footer"
              style={{
                display: 'flex',
                justifyContent: 'center', // Center horizontally
                width: '100%', // Ensure the div takes full width
              }}
            >
              <Button onClick={handleCancel} theme="tertiary">
                Cancel
              </Button>
            </div>
          </LoginOverlay>
  );
}