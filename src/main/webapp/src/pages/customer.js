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

import CustomerForm from '../components/customerForm';

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

const Customer = () => {
  const [openForm, setOpenForm] = React.useState(false);
  const [editItem, setEditItem] = React.useState({});
  const [customers, setCustomers] = React.useState([]);
  const [refresh, setRefresh] = React.useState(0);

  React.useEffect(() => {
    const fetchCustomers = async () => {
      fetch(`http://localhost:${process.env.REACT_APP_BACKEND_PORT}/api/customers/all`)
        .then(async (res) => {
          let data = await res.json();
          setCustomers(data);
        });
    };

    fetchCustomers();
  }, [refresh]);

  const handleSubmit = (event) => {
    if (event.id === 0) {
      fetch(`http://localhost:${process.env.REACT_APP_BACKEND_PORT}/api/customers/save`, {
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
      fetch(`http://localhost:${process.env.REACT_APP_BACKEND_PORT}/api/customers/update`, {
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
    fetch(`http://localhost:${process.env.REACT_APP_BACKEND_PORT}/api/customers/delete/${item.id}`, {
        method: 'DELETE'
      }).then(() => {
        setRefresh(Math.random());
      });
  };

  const CustomerActionsCell = (props) => (
    <ActionsCell {...props} enterEdit={enterEdit} deleteItem={deleteItem} />
  );

  return (
    <>
      <AppBar>
        <AppBarSpacer style={{ width: 8 }} />
        <AppBarSection>
          <button
            className="k-button k-primary"
            onClick={() => enterEdit({ id: 0, name: "", user: { email: "", password: "" }, address: { city: "", street: "", number: 0 }})}
          >
            + Create
          </button>
        </AppBarSection>
      </AppBar>

      <Grid style={{ height: "700px" }} data={customers}>
        <Column field="id" title="Id" width="40px" />
        <Column field="user.id" title="Id User" width="100px" />
        <Column field="address.id" title="Id Address" width="150px" />
        <Column field="name" title="Name" width="150px" />
        <Column field="phone" title="Phone" width="150px" />
        <Column field="user.email" title="Email" width="150px" />
        <Column field="user.password" title="Password" width="150px" />
        <Column field="address.city" title="City" width="150px" />
        <Column field="address.street" title="Street" width="150px" />
        <Column field="address.number" title="Street number" />
        <Column title="Actions" cell={CustomerActionsCell} />
      </Grid>

      {openForm && (
        <CustomerForm
          cancelEdit={handleCancelEdit}
          onSubmit={handleSubmit}
          item={editItem}
        />
      )}
    </>
  );
};

export default Customer;
