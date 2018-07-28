package tech.threekilogram.overscroll;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.ViewGroup;
import tech.threekilogram.overscroll.listener.OnScrollListener;
import tech.threekilogram.overscroll.listener.RecyclerScrollListener;

/**
 * 为 recycler view 增加overScroll 功能,当recyclerView滑动到顶部,可以继续下拉,下拉有阻尼效果,
 * 当当recyclerView滑动到底部,可以继续上拉,上拉有阻尼效果,支持recycler fling, fling 到顶部/底部时,会继续fling
 * <p>
 * {@link #setOverScrollDistance(int)} 设置OverScroll 距离 {@link #setFlingOverScrollDistance(int)} 设置
 * fling 的 OverScroll 距离
 * <p>
 * 只能包含一个 recycler 子view
 *
 * @author wuxio 2018-04-08:6:28
 */
public class OverScrollLayout extends OverScrollContainer {

      public OverScrollLayout (Context context) {

            super(context);
      }

      public OverScrollLayout (Context context, AttributeSet attrs) {

            super(context, attrs);
      }

      public OverScrollLayout (Context context, AttributeSet attrs, int defStyleAttr) {

            super(context, attrs, defStyleAttr);
      }

      //============================ implements method ============================

      @Override
      public void addScrollListener () {

            if(mChild instanceof RecyclerView) {
                  ((RecyclerView) mChild).addOnScrollListener(new RecyclerScrollListener(this));
            }
            if(mChild instanceof NestedScrollView) {

                  ((NestedScrollView) mChild).setOnScrollChangeListener(new OnScrollListener(this));
            }
      }

      //============================layout params============================

      @Override
      protected ViewGroup.LayoutParams generateDefaultLayoutParams () {

            return new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
      }

      @Override
      protected ViewGroup.LayoutParams generateLayoutParams (ViewGroup.LayoutParams p) {

            return new LayoutParams(p);
      }

      @Override
      public ViewGroup.LayoutParams generateLayoutParams (AttributeSet attrs) {

            return new LayoutParams(getContext(), attrs);
      }

      public static class LayoutParams extends OverScrollContainer.LayoutParams {

            public LayoutParams (Context c, AttributeSet attrs) {

                  super(c, attrs);
            }

            public LayoutParams (int width, int height) {

                  super(width, height);
            }

            public LayoutParams (MarginLayoutParams source) {

                  super(source);
            }

            public LayoutParams (ViewGroup.LayoutParams source) {

                  super(source);
            }
      }
}
