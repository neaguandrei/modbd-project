import * as React from "react";
import { Dialog } from "@progress/kendo-react-dialogs";
import { Form, Field, FormElement } from "@progress/kendo-react-form";
import { NumericTextBox } from "@progress/kendo-react-inputs";
import { DropDownList } from "@progress/kendo-react-dropdowns";

const MenuForm = (props) => {
  const [restaurants, setRestaurants] = React.useState([]);
  const [customers, setCustomers] = React.useState([]);
  const [promocodes, setPromocodes] = React.useState([]);
  const [drivers, setDrivers] = React.useState([]);
  const [menus, setMenus] = React.useState([]);

  React.useEffect(() => {
    const fetchData = async () => {
      fetch(`http://localhost:${process.env.REACT_APP_BACKEND_PORT}/api/restaurants/all`)
        .then(async (res) => {
          let data = await res.json();
          setRestaurants(data);
        });

      fetch(`http://localhost:${process.env.REACT_APP_BACKEND_PORT}/api/customers/all`)
        .then(async (res) => {
          let data = await res.json();
          setCustomers(data);
        });

      fetch(`http://localhost:${process.env.REACT_APP_BACKEND_PORT}/api/promo-codes/all`)
        .then(async (res) => {
          let data = await res.json();
          setPromocodes(data);
        });

      fetch(`http://localhost:${process.env.REACT_APP_BACKEND_PORT}/api/drivers/all`)
        .then(async (res) => {
          let data = await res.json();
          setDrivers(data);
        });

      fetch(`http://localhost:${process.env.REACT_APP_BACKEND_PORT}/api/menus/all`)
        .then(async (res) => {
          let data = await res.json();
          setMenus(data);
        });
    };

    fetchData();
  }, []);

  const title = props.item.id === 0 ? 'Create' : `Edit ${props.item.name ?? props.item.id}`;
  return (
    <Dialog title={title} onClose={props.cancelEdit}>
      <Form
        onSubmit={props.onSubmit}
        initialValues={props.item}
        render={(formRenderProps) => (
          <FormElement
            style={{
              maxWidth: 650,
            }}
          >
            <fieldset className={"k-form-fieldset"}>
              <div className="mb-3">
                <Field
                  name={"restaurant"}
                  component={DropDownList}
                  data={restaurants}
                  textField={"name"}
                  label={"Restaurant"}
                />
              </div>

              <div className="mb-3">
                <Field
                  name={"customer"}
                  component={DropDownList}
                  data={customers}
                  textField={"name"}
                  label={"Customer"}
                />
              </div>

              <div className="mb-3">
                <Field
                  name={"promoCode"}
                  component={DropDownList}
                  data={promocodes}
                  textField={"code"}
                  label={"Promo code"}
                />
              </div>

              <div className="mb-3">
                <Field
                  name={"driver"}
                  component={DropDownList}
                  data={drivers}
                  textField={"name"}
                  label={"Driver"}
                />
              </div>

              <div className="mb-3">
                <Field
                  name={"menu"}
                  component={DropDownList}
                  data={menus}
                  textField={"name"}
                  label={"Menu"}
                />
              </div>

              <div className="mb-3">
                <Field
                  name={"quantity"}
                  component={NumericTextBox}
                  label={"Quantity"}
                />
              </div>

              <div className="mb-3">
                <Field
                  name={"totalOrderPrice"}
                  component={NumericTextBox}
                  label={"Total"}
                />
              </div>
            </fieldset>
            <div className="k-form-buttons">
              <button
                type={"submit"}
                className="k-button k-primary"
                disabled={!formRenderProps.allowSubmit}
              >
                {props.item.id === 0 ? 'Create' : 'Update'}
              </button>
              <button
                type={"submit"}
                className="k-button"
                onClick={props.cancelEdit}
              >
                Cancel
              </button>
            </div>
          </FormElement>
        )}
      />
    </Dialog>
  );
};

export default MenuForm;
