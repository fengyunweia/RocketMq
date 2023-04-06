package main.Controller.SheJiModel.Strategy;

/**
 * 策略模式
 * @author fengyunwei
 */
public interface Strategy {
    /**
     * 做操作---计算
     * @param num1 数值1
     * @param num2 数值2
     * @return 计算结果
     */
    public int doOperation(int num1, int num2);

    /**
     * 做加法类
     */
    class Add implements Strategy{

        @Override
        public int doOperation(int num1, int num2) {
            return num1+num2;
        }
    }

    /**
     * 做减法类
     */
    class Sub implements Strategy{

        @Override
        public int doOperation(int num1, int num2) {
            return num1-num2;
        }
    }

    /**
     * 做乘法类
     */
    class Mul implements Strategy{

        @Override
        public int doOperation(int num1, int num2) {
            return num1*num2;
        }
    }

    /**
     * 做除法类
     */
    class Div implements Strategy{

        @Override
        public int doOperation(int num1, int num2) {
            return num1/num2;
        }
    }

    /**
     * 更管策略类
     */
    class Context{
        private Strategy strategy;
        public Context(Strategy strategy){
            this.strategy = strategy;
        }
        public int executeStrategy(int num1, int num2){
            return this.strategy.doOperation(num1,num2);
        }
    }
}

