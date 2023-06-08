import React from 'react';

type Component<P> = React.ComponentType<P & { children: React.ReactElement }>;

export class ProviderBuilder {
  constructor(component: React.FC) {
    this.component = component;
  }
  private component: React.FC;
  private makeFunctionComponent<P>(parent: Component<P>, props: P) {
    const Parent = parent;
    const Child = this.component;
    return (() => (
      <Parent {...props}>
        <Child />
      </Parent>
    )) as React.FC;
  }
  public wrap<P>(parent: Component<P>, props?: P) {
    this.component = this.makeFunctionComponent(parent, props || ({} as P));
    return this;
  }
  public build = () => {
    return this.component;
  };
}
