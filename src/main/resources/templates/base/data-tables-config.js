const exportOptions = {
  columns: ":visible:not(.noExport)",
};

// const excludeColumns = [
//   {
//     targets: 3,
//     className: "noExport",
//   },
// ];

const CLASS_NO_EXPORT = "noExport";

const dataTablesConfig = (fileExportName) => {
  return {
    // dom: "Blfrtip",
    // buttons: ["excel", "pdf", "csv"],
    // dom: '<"top"i>rt<"bottom"flp><"clear">',
    // dom: ' <"p-5"Blfrtip>',
    // dom: '<"float-right"B>',
    dom:
      "<'row m-2'<'col-sm-2 text-center form-control'l><'col-sm-5 text-center'f><'col-sm-5 text-right'B>>" +
      "<'row m-2'<'col-sm-12'tr>>" +
      "<'row m-2'<'col-sm-6'i><'col-sm-6'p>>",
    buttons: [
      {
        extend: "pdfHtml5",
        exportOptions,
        title: fileExportName,
        className: "btn btn-primary",
      },
      {
        extend: "excel",
        exportOptions,
        title: fileExportName,
        className: "btn btn-primary",
      },
      {
        extend: "csv",
        exportOptions,
        title: fileExportName,
        className: "btn btn-primary",
      },
    ],
    lengthMenu: [
      [10, 25, 50, -1],
      [10, 25, 50, "All"],
    ],
  };
};
