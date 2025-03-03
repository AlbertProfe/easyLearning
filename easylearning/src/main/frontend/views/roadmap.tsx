import { ViewConfig } from '@vaadin/hilla-file-router/types.js';

export const config: ViewConfig = {
  menu: { order: 5, icon: 'line-awesome/svg/file.svg' },
  title: 'Roadmap',
  loginRequired: true,
};

export default function DashboardLearningView() {
  return (
    <div className="flex flex-col h-full items-center justify-center p-l text-center box-border">
      <h2>Roadmap</h2>
      <p>Create your own roadmap and share it with others in the community</p>
    </div>
  );
}
