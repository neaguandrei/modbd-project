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

import PromoCodeForm from '../components/promoCodeForm';

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

const PromoCode = () => {
  const [openForm, setOpenForm] = React.useState(false);
  const [editItem, setEditItem] = React.useState({});
  const [promoCodes, setPromoCodes] = React.useState([]);
  const [refresh, setRefresh] = React.useState(0);

  React.useEffect(() => {
    const fetchPromoCodes = async () => {
      fetch(`http://localhost:${process.env.REACT_APP_BACKEND_PORT}/api/promo-codes/all`)
        .then(async (res) => {
          let data = await res.json();
          setPromoCodes(data);
        });
    };

    fetchPromoCodes();
  }, [refresh]);

  const handleSubmit = (event) => {
    if (event.id === 0) {
      fetch(`http://localhost:${process.env.REACT_APP_BACKEND_PORT}/api/promo-codes/save`, {
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
      fetch(`http://localhost:${process.env.REACT_APP_BACKEND_PORT}/api/promo-codes/update`, {
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
    fetch(`http://localhost:${process.env.REACT_APP_BACKEND_PORT}/api/promo-codes/delete/${item.id}`, {
        method: 'DELETE'
      }).then(() => {
        setRefresh(Math.random());
      });
  };

  const PromoCodeActionsCell = (props) => (
    <ActionsCell {...props} enterEdit={enterEdit} deleteItem={deleteItem} />
  );

  return (
    <>
      <AppBar>
        <AppBarSpacer style={{ width: 8 }} />
        <AppBarSection>
          <button
            className="k-button k-primary"
            onClick={() => enterEdit({ id: 0, name: "", value: "" })}
          >
            + Create
          </button>
        </AppBarSection>
      </AppBar>

      <Grid style={{ height: "700px" }} data={promoCodes}>
        <Column field="id" title="Id" width="150px" />
        <Column field="code" title="Code" />
        <Column field="value" title="Value" />
        <Column title="Actions" cell={PromoCodeActionsCell} />
      </Grid>

      {openForm && (
        <PromoCodeForm
          cancelEdit={handleCancelEdit}
          onSubmit={handleSubmit}
          item={editItem}
        />
      )}
    </>
  );
};

export default PromoCode;
