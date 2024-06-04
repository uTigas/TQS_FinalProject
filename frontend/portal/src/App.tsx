import { Redirect, Route } from 'react-router-dom';
import {
  IonApp,
  IonHeader,
  IonIcon,
  IonLabel,
  IonRouterOutlet,
  IonTabBar,
  IonTabButton,
  IonTabs,
  setupIonicReact
} from '@ionic/react';
import { IonReactRouter } from '@ionic/react-router';
import { ellipse, square, triangle } from 'ionicons/icons';
import Tab2 from './pages/Tab2';
import Dashboard from './pages/Dashboard';
import StationPage from './pages/StationPage';
import ConnectionPage from './pages/ConnectionPage';
import './App.css';
/* Core CSS required for Ionic components to work properly */
import '@ionic/react/css/core.css';

/* Basic CSS for apps built with Ionic */
import '@ionic/react/css/normalize.css';
import '@ionic/react/css/structure.css';
import '@ionic/react/css/typography.css';

/* Optional CSS utils that can be commented out */
import '@ionic/react/css/padding.css';
import '@ionic/react/css/float-elements.css';
import '@ionic/react/css/text-alignment.css';
import '@ionic/react/css/text-transformation.css';
import '@ionic/react/css/flex-utils.css';
import '@ionic/react/css/display.css';

/**
 * Ionic Dark Mode
 * -----------------------------------------------------
 * For more info, please see:
 * https://ionicframework.com/docs/theming/dark-mode
 */

/* import '@ionic/react/css/palettes/dark.always.css'; */
/* import '@ionic/react/css/palettes/dark.class.css'; */
import '@ionic/react/css/palettes/dark.system.css';

/* Theme variables */
import './theme/variables.css';
import FooterAdmin from './components/FooterAdmin';
import Footer from './components/Footer';
import { useContext, useEffect } from 'react';
import { SharedVariablesContext, SharedVariablesProvider, User } from './support/Variables';
import TrainPage from './pages/Train Page';
import APIWrapper from './components/APIWrapper';
import Tab3 from './pages/Tab3';

setupIonicReact();


const App: React.FC = () => {
  const {loggedUser, setLoggedUser} = useContext(SharedVariablesContext)
  useEffect(() => {
    APIWrapper.fetchUserDetails()
    .then((response: Response | undefined) => {
        if (response && response.ok) {
            return response.json();
        } else {
            throw new Error('Failed to fetch data');
        }
    })
    .then(userData => {
        const newUser : User = {username: userData.username, name: userData.name, role: userData.role }
        setLoggedUser(newUser)
    })
    .catch(error => {
        console.error('Error:', error.message);
        setLoggedUser(null)
    })
  }, [])
  return (
    <SharedVariablesProvider>
      <IonApp>
        <IonReactRouter>
          <IonTabs>
            <IonRouterOutlet>
              <Route exact path="/tab2">
                <Tab2 />
              </Route>
              <Route exact path="/">
                <Tab2 />
              </Route>
              <Route exact path="/tickets">
                <Tab3 />
              </Route>
              <Route exact path="/admin/dashboard">
                <Dashboard />
              </Route>
              <Route path="/admin/stations">
                <StationPage />
              </Route>
              <Route path="/admin/trains">
                <TrainPage />
              </Route>
              <Route exact path="/admin">
                <Redirect to="/admin/dashboard" />
              </Route>
            </IonRouterOutlet>
            <IonTabBar slot="bottom">
              
            {loggedUser !== null ? (
              loggedUser.role === "ADMIN" ? (
                <IonTabButton tab="adminDashboard" href="/admin">
                  <IonIcon aria-hidden="true" icon={triangle} />
                  <IonLabel>Dashboard</IonLabel>
                </IonTabButton>
              ) : (
                <IonTabButton tab="tab1" href="/tab1">
                  <IonIcon aria-hidden="true" icon={triangle} />
                  <IonLabel>Dashboard</IonLabel>
                </IonTabButton>
              )
            ) : (
              <IonTabButton tab="home" href="/">
                <IonIcon aria-hidden="true" icon={triangle} />
                <IonLabel>Homepage</IonLabel>
              </IonTabButton>
            )}

            </IonTabBar>
          </IonTabs>
        </IonReactRouter>
      </IonApp>
    </SharedVariablesProvider>
  )
};

export default App;
