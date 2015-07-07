describe('ViewModel: Worker', function () {
  it('should not allow a worker with no name', function () {
    expect(function(){
      Worker.build({
          first_name: "Pepe",
          last_name: "Sanchez",
          mail: "lala@lala.com",
          birthdate: "Oct 29, 1990"
    })
    }).toThrow();
  });
});
