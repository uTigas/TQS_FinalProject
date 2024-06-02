// FooterAdmin.tsx

import React from 'react';
import { IonTabBar, IonTabButton, IonIcon, IonLabel } from '@ionic/react';
import { triangle, ellipse, square } from 'ionicons/icons';

const FooterAdmin: React.FC = () => {
  return (
      <><IonTabButton tab="dashboard" href="/dashboard">
      <IonIcon aria-hidden="true" icon={triangle} />
      <IonLabel>Dashboard</IonLabel>
    </IonTabButton><IonTabButton tab="tab2" href="/tab2">
        <IonIcon aria-hidden="true" icon={ellipse} />
        <IonLabel>Stations</IonLabel>
      </IonTabButton><IonTabButton tab="tab3" href="/tab3">
        <IonIcon aria-hidden="true" icon={square} />
        <IonLabel>Tab 3</IonLabel>
      </IonTabButton></>
  );
};

export default FooterAdmin;
