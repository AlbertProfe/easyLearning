import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import { useSignal } from '@vaadin/hilla-react-signals';
import { Button, Dialog, TextField } from '@vaadin/react-components';
import { useNavigate } from 'react-router-dom';
import { useAuth } from 'Frontend/util/auth.js';

export const config: ViewConfig = {
  menu: { exclude: true },
  flowLayout: false,
};

export default function LoginView() {
  const navigate = useNavigate();
  const { login } = useAuth();
  const username = useSignal('');
  const password = useSignal('');
  const error = useSignal('');

  const handleLogin = async () => {
    try {
      const result = await login(username.value, password.value);
      if (result.success) {
        navigate('/dashboard'); // Redirect to dashboard or another page after successful login
      } else {
        error.value = result.message || 'Login failed';
      }
    } catch (err) {
      error.value = 'An error occurred during login';
    }
  };

  return (
    <Dialog
      opened
      headerTitle="Login"
      onClosed={() => navigate(-1)}
    >
      <div className="flex flex-col gap-m">

        <p className="text-center">Sign in to your account</p>
        <TextField
          label="Username"
          value={username.value}
          onValueChanged={(e) => (username.value = e.detail.value)}
          className="w-full"
        />
        <TextField
          label="Password"
          type="password"
          value={password.value}
          onValueChanged={(e) => (password.value = e.detail.value)}
          className="w-full"
        />
        <Button theme="primary" onClick={handleLogin} className="w-full">
          Login
        </Button>
        {error.value && <p className="text-red-500 text-center">{error.value}</p>}

      <p slot="footer" className="text-center">
        Don't have an account?{' '}
        <a href="/register" className="text-blue-500 hover:underline">
          Register here
        </a>
      </p>
</div>
    </Dialog>

  );
}