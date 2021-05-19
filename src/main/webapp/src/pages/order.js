import React from 'react';

import {
  Grid,
  GridColumn as Column,
} from "@progress/kendo-react-grid";

import {
  AppBar,
  AppBarSection,
  AppBarSpacer
} from "@progress/kendo-react-layout";

import OrderForm from '../components/orderForm';

const ActionsCell = (props) => {
  return (
    <td>
      <button
        className="k-button k-primary"
        onClick={() => props.enterEdit(props.dataItem)}
      >
        Edit
      </button>
      &nbsp;
      <button
        className="k-button k-primary"
        onClick={() => props.deleteItem(props.dataItem)}
      >
        Delete
      </button>
    </td>
  );
};

const Order = () => {
  const [openForm, setOpenForm] = React.useState(false);
  const [editItem, setEditItem] = React.useState({});
  const [orders, setOrders] = React.useState([]);
  const [refresh, setRefresh] = React.useState(0);

  React.useEffect(() => {
    const fetchOrders = async () => {
      fetch(`http://localhost:${process.env.REACT_APP_BACKEND_PORT}/api/orders/all`)
        .then(async (res) => {
          let data = await res.json();
          setOrders(data);
        });
    };

    fetchOrders();
  }, [refresh]);

  const handleSubmit = (event) => {
    if (event.id === 0) {
      fetch(`http://localhost:${process.env.REACT_APP_BACKEND_PORT}/api/orders/save`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(event)
      }).then(() => {
        setOpenForm(false);
        setRefresh(Math.random());
      });
    } else {
      fetch(`http://localhost:${process.env.REACT_APP_BACKEND_PORT}/api/orders/update`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(event)
      }).then(() => {
        setOpenForm(false);
        setRefresh(Math.random());
      });
    }
  };

  const handleCancelEdit = () => {
    setOpenForm(false);
  };

  const enterEdit = (item) => {
    setOpenForm(true);
    setEditItem(item);
  };

  const deleteItem = (item) => {
    fetch(`http://localhost:${process.env.REACT_APP_BACKEND_PORT}/api/orders/delete/${item.id}`, {
        method: 'DELETE'
      }).then(() => {
        setRefresh(Math.random());
      });
  };

  const OrderActionsCell = (props) => (
    <ActionsCell {...props} enterEdit={enterEdit} deleteItem={deleteItem} />
  );

  return (
    <>
      <AppBar>
        <AppBarSpacer style={{ width: 8 }} />
        <AppBarSection>
          <button
            className="k-button k-primary"
            onClick={() => enterEdit({ id: 0, quantity: 0, totalOrderPrice: 0.0, orderDate: { created: new Date(), completed: new Date() }, promoCode: {}, driver: {}, menu: {}, restaurant: {}, customer: {} })}
          >
            + Create
          </button>
        </AppBarSection>
      </AppBar>

      <Grid style={{ height: "700px" }} data={orders}>
        <Column field="id" title="Id" width="150px" />
        <Column field="restaurant.id" title="Id Restaurant" width="150px" />
        <Column field="customer.id" title="Id Customer" width="150px" />
        <Column field="promoCode.id" title="Id Promo" width="150px" />
        <Column field="driver.id" title="Id Driver" width="150px" />
        <Column field="menu.id" title="Id Menu" width="150px" />
        <Column field="orderDate.created" title="Created date" width="200px" />
        <Column field="orderDate.completed" title="Completed date" width="200px" />
        <Column field="quantity" title="Quantity" width="150px" />
        <Column field="totalOrderPrice" title="Total" width="150px" />
        <Column title="Actions" cell={OrderActionsCell} />
      </Grid>

      {openForm && (
        <OrderForm
          cancelEdit={handleCancelEdit}
          onSubmit={handleSubmit}
          item={editItem}
        />
      )}
    </>
  );
};

export default Order;
