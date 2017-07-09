import { TodoAngularUIPage } from './app.po';

describe('todo-angular-ui App', () => {
  let page: TodoAngularUIPage;

  beforeEach(() => {
    page = new TodoAngularUIPage();
  });

  it('should display welcome message', done => {
    page.navigateTo();
    page.getParagraphText()
      .then(msg => expect(msg).toEqual('Welcome to td!!'))
      .then(done, done.fail);
  });
});
