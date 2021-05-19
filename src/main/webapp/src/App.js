import {
  BrowserRouter,
  Switch,
  Route,
  Link
} from 'react-router-dom';

import {
  AppBar,
  AppBarSection,
  AppBarSpacer
} from "@progress/kendo-react-layout";

import Category from './pages/category';
import Restaurant from './pages/restaurant';
import PromoCode from './pages/promoCode';
import Menu from './pages/menu';
import Customer from './pages/customer';
import Driver from './pages/driver';
import Order from './pages/order';

import '@progress/kendo-theme-material/dist/all.css';
import './style/appbar.css';
import './style/dropdown.css';

function App() {
  return (
    <>
      <BrowserRouter>
      <AppBar>
        <AppBarSection>
          <h4>MODBD Dashboard</h4>
        </AppBarSection>
        <AppBarSpacer style={{ width: 8 }} />
        <AppBarSection>
            <ul>
              <li>
                <Link to="/customer">Customers</Link>
              </li>
              <li>
                <Link to="/driver">Drivers</Link>
              </li>
              <li>
                <Link to="/order">Orders</Link>
              </li>
              <li>
                <Link to="/restaurant">Restaurants</Link>
              </li>
              <li>
                <Link to="/category">Categories</Link>
              </li>
              <li>
                <Link to="/menu">Menus</Link>
              </li>
              <li>
                <Link to="/promocode">Promo codes</Link>
              </li>
            </ul>
          </AppBarSection>
        </AppBar>

        <Switch>
          <Route exact path='/'>
            <Customer />
          </Route>
          <Route path='/order'>
            <Order />
          </Route>
          <Route path='/restaurant'>
            <Restaurant />
          </Route>
          <Route path='/promocode'>
            <PromoCode />
          </Route>
          <Route path='/category'>
            <Category />
          </Route>
          <Route path='/menu'>
            <Menu />
          </Route>
          <Route path='/customer'>
            <Customer />
          </Route>
          <Route path='/driver'>
            <Driver />
          </Route>
        </Switch>
      </BrowserRouter>
    </>
  );
}

export default App;
