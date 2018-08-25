package com.gkshanmugavel.newapp;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.annotation.IdRes;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.gkshanmugavel.newapp.IdlingResource.CustomIdlingResource;
import com.gkshanmugavel.newapp.model.TitleModel;
import com.gkshanmugavel.newapp.view.HomeActivity;
import com.gkshanmugavel.newapp.view.RowAdapter;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertEquals;
import static org.hamcrest.core.AllOf.allOf;
import static org.mockito.Mockito.mock;

@RunWith(AndroidJUnit4.class)
public class HomeActivityTest {

    @Rule
    public ActivityTestRule<HomeActivity> activityRule = new ActivityTestRule<>(HomeActivity.class);

    private static TitleModel mockedTitleModel;
    int COUNT = 0;

    @BeforeClass
    public static void setUp() throws InterruptedException {
        Utils.App.Open("com.gkshanmugavel.newapp");
        Thread.sleep(10000);
        //Create mock object of TitleModel
        mockedTitleModel = mock(TitleModel.class);
    }

    @Before
    public void init() {
        IdlingRegistry.getInstance().register(CustomIdlingResource.getIdlingResource());
    }

    @After
    public void unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(CustomIdlingResource.getIdlingResource());
    }


    @Test
    public void testMockData() {
        List mockList = Mockito.mock(ArrayList.class);
        mockList.add(mockedTitleModel);
        Mockito.verify(mockList).add(mockedTitleModel);
        assertEquals(0, mockList.size());
    }

    /**
     * check the title for initial
     */
    @Test
    public void checkTitle() {
        onView(withText(activityRule.getActivity().getString(R.string.click_list)))
                .check(matches(withText(activityRule.getActivity().getString(R.string.click_list))));
    }

    /**
     * check the title for initial with rotation
     */

    @Test
    public void checkTitleOnRotation() {
        onView(withText(activityRule.getActivity().getString(R.string.click_list)))
                .check(matches(withText(activityRule.getActivity().getString(R.string.click_list))));
        rotateScreen();
        onView(withText(activityRule.getActivity().getString(R.string.click_list)))
                .check(matches(withText(activityRule.getActivity().getString(R.string.click_list))));
    }

    /**
     * Scroll to First position and click the list and match the title
     */
    @Test
    public void scrollToFirstPositionClick() {
        int position = 0;
        onView(withId(R.id.rv_items))
                .perform(actionOnItemAtPosition(position, click()));
        TitleModel mock = activityRule.getActivity().adapter.getItemAt(position);
        onView(withId(R.id.toolbar_title)).check(matches(withText(mock.getTitle())));
    }

    /**
     * Scroll to first position and click the list and match the title and Rotation the screen
     * and click list item again the match the title
     */
    @Test
    public void scrollToFirstPositionClickRotation() {
        int position = 0;
        onView(withId(R.id.rv_items))
                .perform(actionOnItemAtPosition(position, click()));
        TitleModel mock = activityRule.getActivity().adapter.getItemAt(position);
        onView(withId(R.id.toolbar_title)).check(matches(withText(mock.getTitle())));
        rotateScreen();
        onView(withId(R.id.toolbar_title)).check(matches(withText(mock.getTitle())));
    }

    /**
     * Scroll to last position and click the list and match the title
     */
    @Test
    public void scrollToLastPositionClick() {
        int position = getCountFromRecyclerView(R.id.rv_items) - 1;
        onView(withId(R.id.rv_items))
                .perform(actionOnItemAtPosition(position, click()));
        TitleModel mock = activityRule.getActivity().adapter.getItemAt(position);
        onView(withId(R.id.toolbar_title)).check(matches(withText(mock.getTitle())));
    }

    /**
     * Scroll to last position and click the list and match the title and Rotation the screen
     * and click list item again the match the title
     */
    @Test
    public void scrollToLastPositionClickRotation() {
        int position = getCountFromRecyclerView(R.id.rv_items) - 1;
        onView(withId(R.id.rv_items))
                .perform(actionOnItemAtPosition(position, click()));
        TitleModel mock = activityRule.getActivity().adapter.getItemAt(position);
        onView(withId(R.id.toolbar_title)).check(matches(withText(mock.getTitle())));
        rotateScreen();
        onView(withId(R.id.toolbar_title)).check(matches(withText(mock.getTitle())));
    }

    /**
     * Scroll to First position to last position
     */

    @Test
    public void scrollToFirstScrollToLastPosition() {
        int position = getCountFromRecyclerView(R.id.rv_items) - 1;
        onView(withId(R.id.rv_items))
                .perform(scrollToPosition(0));
        onView(withId(R.id.rv_items))
                .perform(scrollToPosition(position));
    }

    /**
     * Scroll to Last position to first position
     */
    @Test
    public void scrollToLastScrollToFirstPosition() {
        int position = getCountFromRecyclerView(R.id.rv_items) - 1;

        onView(withId(R.id.rv_items))
                .perform(scrollToPosition(position));
        onView(withId(R.id.rv_items))
                .perform(scrollToPosition(0));
    }

    /**
     * Scroll to 3rd position
     */
    @Test
    public void ScrollTo4thPositionClick() {
        int position = 3;

        onView(withId(R.id.rv_items))
                .perform(actionOnItemAtPosition(position, click()));
        TitleModel mock = activityRule.getActivity().adapter.getItemAt(position);
        onView(withId(R.id.toolbar_title)).check(matches(withText(mock.getTitle())));
    }

    /**
     * Scroll to 3rd position
     */
    @Test
    public void ScrollTo4thdPositionClick() {
        int position = 3;

        onView(withId(R.id.rv_items))
                .perform(actionOnItemAtPosition(position, click()));
        TitleModel mock = activityRule.getActivity().adapter.getItemAt(position);
        onView(withId(R.id.toolbar_title)).check(matches(withText(mock.getTitle())));
        rotateScreen();
        onView(withId(R.id.toolbar_title)).check(matches(withText(mock.getTitle())));
    }


    @Test
    public final void shouldContainCertainListItem() {
        List<TitleModel> asList = Arrays.asList(new TitleModel("title", "description", "image"));
        final List<TitleModel> mockedList = mock(List.class);
        mockedList.addAll(asList);

    }

    /**
     * Rotate the screen
     */
    private void rotateScreen() {
        Context context = InstrumentationRegistry.getTargetContext();
        int orientation = context.getResources().getConfiguration().orientation;

        Activity activity = activityRule.getActivity();
        activity.setRequestedOrientation(
                (orientation == Configuration.ORIENTATION_PORTRAIT) ?
                        ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE : ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    /**
     *  Match the text base on Recycler view holder
     * @param text
     * @return
     */
    public static Matcher<RecyclerView.ViewHolder> withHolderTimeView(final String text) {
        return new BoundedMatcher<RecyclerView.ViewHolder, RowAdapter.MyViewHolder>(RowAdapter.MyViewHolder.class) {

            @Override
            protected boolean matchesSafely(RowAdapter.MyViewHolder item) {
                TextView timeViewText = (TextView) item.itemView.findViewById(R.id.tv_title);
                if (timeViewText == null) {
                    return false;
                }
                return timeViewText.getText().toString().contains(text);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText(text);
            }
        };
    }

    /*public static ScrollToPositionViewAction smoothScrollTo(int position) {
        return new ScrollToPositionViewAction(position);
    }*/

    /**
     * Method Name : getRecyclerViewCount
     * Arguments :  matcher -  view which you want to match
     * Returns: count - Item count of the recyclerView
     * Method Description : Use this method to get the item count of a recyclerView
     */
    public int getCountFromRecyclerView(@IdRes int RecyclerViewId) {

        Matcher matcher = new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View item) {
                COUNT = ((RecyclerView) item).getAdapter().getItemCount();
                return true;
            }

            @Override
            public void describeTo(Description description) {
            }
        };
        onView(allOf(withId(RecyclerViewId), isDisplayed())).check(matches(matcher));
        int result = COUNT;
        COUNT = 0;
        return result;
    }
}